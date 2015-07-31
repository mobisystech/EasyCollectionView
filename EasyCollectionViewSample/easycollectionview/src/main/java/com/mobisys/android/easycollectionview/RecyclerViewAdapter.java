package com.mobisys.android.easycollectionview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mobisys.android.easycollectionview.annotations.ViewId;
import com.mobisys.android.easycollectionview.utils.MImageLoader;
import com.mobisys.android.easycollectionview.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by priyank on 7/10/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private Context mContext;
    private Class mModelClass;
    private int mRowLayoutId;
    private List<? extends Object> arrayList;
    private List<EasyCollectionAdapter.Mapping> methodMapping = new ArrayList<EasyCollectionAdapter.Mapping>();
    private HashMap<Integer, OnViewIdClickListener> mViewIdClickListeners;
    private HashMap<Integer, ViewIdBinder> mViewIdBinders;

    public RecyclerViewAdapter(Context context, List<? extends Object> objects, String modelClassName, int rowLayoutId){
        this.mContext = context;
        this.mRowLayoutId = rowLayoutId;
        this.arrayList = objects;

        initMethods(modelClassName);
    }

    private void initMethods(String modelClassName){
        try {
            mModelClass = Class.forName(modelClassName);
            methodMapping = new ArrayList<EasyCollectionAdapter.Mapping>();

            List<Method> methods = ReflectionUtils.getFieldsUpTo(mModelClass, Object.class);
            for (int i=0;i<methods.size();i++){
                ViewId viewId = methods.get(i).getAnnotation(ViewId.class);
                if (viewId !=null){
                    EasyCollectionAdapter.Mapping mapping = new EasyCollectionAdapter.Mapping(viewId.id(), methods.get(i));
                    methodMapping.add(mapping);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rowView = View.inflate(mContext, mRowLayoutId, null);
        return new RecyclerViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, final int position) {
        final View convertView = recyclerViewHolder.rowView;
        for (int i=0; i<methodMapping.size(); i++){
            Method method = methodMapping.get(i).method;
            int id = methodMapping.get(i).id;
            try {
                final View v = convertView.findViewById(id);
                if (v instanceof TextView){
                    String text = method.invoke(arrayList.get(position), null).toString();
                    ((TextView)v).setText(text);
                } else if (v instanceof ImageView){
                    String url = method.invoke(arrayList.get(position), null).toString();
                    MImageLoader.displayImage(mContext, url, (ImageView) v, R.drawable.picture_stub);
                } else if (v instanceof Button){
                    String text = method.invoke(arrayList.get(position), null).toString();
                    ((Button)v).setText(text);
                } else if (v instanceof RatingBar){
                    Object result = method.invoke(arrayList.get(position), null);
                    if (result instanceof Float || result instanceof Integer){
                        Float rate = (Float)result;
                        ((RatingBar)v).setRating(rate);
                    }
                }

                if (mViewIdClickListeners!=null && mViewIdClickListeners.size()>0){
                    Iterator<Integer> keys = mViewIdClickListeners.keySet().iterator();
                    while(keys.hasNext()){
                        final int viewId = keys.next();
                        convertView.findViewById(viewId).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OnViewIdClickListener listener = mViewIdClickListeners.get(viewId);
                                listener.onViewIdClickListener(position, view, convertView);
                            }
                        });
                    }
                }

                if (mViewIdBinders!=null && mViewIdBinders.size()>0) {
                    Iterator<Integer> keys = mViewIdBinders.keySet().iterator();
                    while (keys.hasNext()) {
                        final int viewId = keys.next();
                        View rowViewId = convertView.findViewById(viewId);
                        ViewIdBinder viewIdBinder = mViewIdBinders.get(viewId);
                        viewIdBinder.bindViewId(position, rowViewId, convertView);
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }

    public void setViewIdClickListeners(HashMap<Integer, OnViewIdClickListener> viewIdClickListeners){
        mViewIdClickListeners = viewIdClickListeners;
    }

    public void setViewIdBinders(HashMap<Integer, ViewIdBinder> viewIdBinders){
        mViewIdBinders = viewIdBinders;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public View rowView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            this.rowView = itemView;
        }
    }
}
