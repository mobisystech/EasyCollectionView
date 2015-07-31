package com.mobisys.android.easycollectionview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListView;

import com.mobisys.android.easycollectionview.exceptions.ModelTypeMismatchException;
import com.mobisys.android.easycollectionview.exceptions.NoModelDefinedException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mobisystech on 7/1/15.
 */
public class EasyGridView extends GridView {
    private Context mContext;
    private String mModelClassName;
    private int mRowLayoutId;
    private EasyCollectionAdapter mAdapter;
    private HashMap<Integer, OnViewIdClickListener> mViewIdClickListeners;
    private HashMap<Integer, ViewIdBinder> mViewIdBinders;

    public EasyGridView(Context context) {
        super(context);
        this.mContext = context;
    }

    public EasyGridView(Context context, AttributeSet attrs) throws NoModelDefinedException {
        super(context, attrs);
        this.mContext = context;
        initAttrs(context, attrs);
    }

    public EasyGridView(Context context, AttributeSet attrs, int defStyleAttr) throws NoModelDefinedException {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EasyGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) throws NoModelDefinedException {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) throws NoModelDefinedException {
        if (attrs!=null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EasyCollectionView);
            mModelClassName = a.getString(R.styleable.EasyCollectionView_modelClass);
            mRowLayoutId = a.getResourceId(R.styleable.EasyCollectionView_rowLayout, android.R.layout.simple_dropdown_item_1line);
        }

        if (mModelClassName == null) {
            throw new NoModelDefinedException();
        }
    }

    public void setArrayList(List<? extends Object> arrayList) throws ModelTypeMismatchException {
        if (arrayList!=null && arrayList.size()>0){
            if (!arrayList.get(0).getClass().getName().equals(mModelClassName)){
                throw new ModelTypeMismatchException("Model type "+mModelClassName+" does not match with "+arrayList.get(0).getClass().getName());
            }

            mAdapter = new EasyCollectionAdapter(mContext, arrayList, mModelClassName, mRowLayoutId);
            mAdapter.setViewIdClickListeners(mViewIdClickListeners);
            setAdapter(mAdapter);
        }
    }

    public void setOnViewIdClickListener(int viewId, OnViewIdClickListener listener){
        if (mViewIdClickListeners == null){
            mViewIdClickListeners = new HashMap<>();
        }

        mViewIdClickListeners.put(viewId, listener);
        if (mAdapter!=null) mAdapter.setViewIdClickListeners(mViewIdClickListeners);
    }

    public void bindViewId(int viewId, ViewIdBinder viewIdBinder){
        if (mViewIdBinders == null){
            mViewIdBinders = new HashMap<>();
        }

        mViewIdBinders.put(viewId, viewIdBinder);
        if (mAdapter!=null) mAdapter.setViewIdBinders(mViewIdBinders);
    }
}
