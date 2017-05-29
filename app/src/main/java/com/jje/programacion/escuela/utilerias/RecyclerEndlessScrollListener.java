package com.jje.programacion.escuela.utilerias;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

class RecyclerEndlessScrollListener extends RecyclerView.OnScrollListener {
    private boolean isLoading;
    private boolean hasMorePages;
    private int pageNumber = 0;
    private RefreshList refreshList;
    private boolean isRefreshing;
    private int pastVisibleItems;

    public RecyclerEndlessScrollListener(RefreshList refreshList) {
        this.isLoading = false;
        this.hasMorePages = true;
        this.refreshList = refreshList;
    }

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

        int visibleItemCount = manager.getChildCount();
        int totalItemCount = manager.getItemCount();
        int[] firstVisibleItems = manager.findFirstVisibleItemPositions(null);
        if (firstVisibleItems != null && firstVisibleItems.length > 0) {
            pastVisibleItems = firstVisibleItems[0];
            Log.e("jma","se ha llegado al ultimo item");
        }

        if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
            isLoading = true;
            if (hasMorePages && !isRefreshing) {
                isRefreshing = true;
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        refreshList.onRefresh(pageNumber);
                    }
                }, 200);
            }
        } else {
            isLoading = false;
        }
    }

    public void noMorePages() {
        this.hasMorePages = false;
    }

    void notifyMorePages() {
        isRefreshing = false;
        pageNumber = pageNumber + 1;
    }

    interface RefreshList {
        void onRefresh(int pageNumber);
    }
}