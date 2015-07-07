package com.mobisys.android.easycollectionview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import java.util.Set;

/**
 * Created by mahavir on 7/1/15.
 */
public class EasyCollectionAdapter extends BaseAdapter {
    private Context mContext;
    private Class mModelClass;
    private int mRowLayoutId;
    private HashMap<Integer, OnViewIdClickListener> mViewIdClickListeners;
    private HashMap<Integer, ViewIdBinder> mViewIdBinders;

    private List<? extends Object> arrayList = new ArrayList<Object>();
    private List<Mapping> methodMapping = new ArrayList<Mapping>();

    private static class Mapping {
        public int id;
        public Method method;

        public Mapping(int id, Method method){
            this.id = id;
            this.method = method;
        }
    }

    public EasyCollectionAdapter(Context context, List<? extends Object> objects, String modelClassName, int rowLayoutId, String viewIdClick){
        this.mContext = context;
        this.mRowLayoutId = rowLayoutId;
        this.arrayList = objects;

        initMethods(modelClassName, viewIdClick);
    }

    private void initMethods(String modelClassName, String viewIdClick){
        try {
            mModelClass = Class.forName(modelClassName);
            methodMapping = new ArrayList<Mapping>();

            List<Method> methods = ReflectionUtils.getFieldsUpTo(mModelClass, Object.class);
            for (int i=0;i<methods.size();i++){
                ViewId viewId = methods.get(i).getAnnotation(ViewId.class);
                if (viewId !=null){
                    Mapping mapping = new Mapping(viewId.id(), methods.get(i));
                    methodMapping.add(mapping);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mRowLayoutId, parent, false);
        }

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
                                listener.onViewIdClickListener(view, position);
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
                        viewIdBinder.bindViewId(rowViewId, position);
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        return convertView;
    }

    public void setViewIdClickListeners(HashMap<Integer, OnViewIdClickListener> viewIdClickListeners){
        mViewIdClickListeners = viewIdClickListeners;
    }

    public void setViewIdBinders(HashMap<Integer, ViewIdBinder> viewIdBinders){
        mViewIdBinders = viewIdBinders;
    }
}
