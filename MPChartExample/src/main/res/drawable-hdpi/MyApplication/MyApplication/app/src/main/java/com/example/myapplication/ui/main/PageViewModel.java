package com.example.myapplication.ui.main;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.AndroidViewModel;

import com.example.myapplication.R;

public class PageViewModel extends AndroidViewModel {

    private final Context mContext;

    public PageViewModel(@NonNull Application application) {
        super(application);

        mContext = application.getApplicationContext();
    }

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {

            String buffer = null;

            switch(input)
            {
                case 1:
                {
                    buffer = mContext.getResources().getString(R.string.tab_describe_1);

                    break;
                }

                case 2:
                {
                    buffer = mContext.getResources().getString(R.string.tab_describe_2);

                    break;
                }

                case 3:
                {
                    buffer = mContext.getResources().getString(R.string.tab_describe_3);

                    break;
                }

                default: break;
            }

            return buffer;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}