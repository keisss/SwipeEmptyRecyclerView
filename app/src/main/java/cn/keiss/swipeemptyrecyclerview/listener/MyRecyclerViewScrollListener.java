package cn.keiss.swipeemptyrecyclerview.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;

/**
 * Control the visible of footerView by scroll lens
 * Created by hekai on 2017/8/4.
 */

public abstract class MyRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private boolean isShow =true;
    private int yLens;
    private  int yScrollLens = 10;


    protected MyRecyclerViewScrollListener() {
        super();

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (yLens >yScrollLens && isShow){//向下滑动大于25且显示
            hide();
            isShow =false;
            yLens =0;
        }
        if(yLens <-yScrollLens && !isShow){//向上滑动大于25且隐藏
            show();
            isShow = true;
            yLens = 0;
        }
        //在显示且向下滑动时增加滑动距离判断，在隐藏且向上滑动的时候记录滑动距离
        if ((isShow && dy>0)||(!isShow && dy<0)){
            yLens+=dy;
        }

    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);


        switch (newState){
            case SCROLL_STATE_IDLE:
                LinearLayoutManager manager =  (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见item的position
                int lastItem = manager.findLastVisibleItemPosition();
                //如果最后一个可见item为所有数据的最后一个 并且滑动停止，避免多次进行网络请求
                if (lastItem == recyclerView.getAdapter().getItemCount()-1){
                    updateDataFromBottom();
                }
                break;

        }



    }

    public void setScrollLensToHide(int lens){
        this.yScrollLens = lens;
    }

    public abstract void show();
    public abstract void hide();
    public abstract void updateDataFromBottom();
}
