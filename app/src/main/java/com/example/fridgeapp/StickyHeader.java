package com.example.fridgeapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickyHeader extends RecyclerView.ItemDecoration
{
    Context context;
    int headerOffset;
    boolean sticky;
    SectionCallBack sectionCallBack;
    View headerView;
    TextView tvTitle;

    public StickyHeader(Context con, int headerHeight, boolean isSticky, SectionCallBack callback)
    {
        context = con;
        headerOffset = headerHeight;
        sticky = isSticky;
        sectionCallBack = callback;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        if(sectionCallBack.isSectionHeader(pos))
        {
            outRect.top = headerOffset;
        }
    }

    public interface SectionCallBack{
        public boolean isSectionHeader(int pos);
        public String getSectionHeaderName(int pos);
    }


    private View inflateHeader(RecyclerView recyclerView)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.header_list_layout,recyclerView,false);
        return view;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if(headerView == null)
        {
            headerView = inflateHeader(parent);
            tvTitle = (TextView) headerView.findViewById(R.id.headerText);
            fixLayoutSize(headerView, parent);
        }
        String prevTitle = "";
        for(int i = 0; i < parent.getChildCount();i++)
        {
            View child = parent.getChildAt(i);
            int childPos = parent.getChildAdapterPosition(child);
            String title = sectionCallBack.getSectionHeaderName(childPos);
            if(!prevTitle.equalsIgnoreCase(title)|| sectionCallBack.isSectionHeader(childPos))
            {
                drawHeader(c, child, headerView);
                prevTitle = title;
            }
        }
    }

    private void drawHeader(Canvas c, View child, View headerView)
    {
        c.save();
        if(sticky)
        {
            c.translate(0,Math.max(0,child.getTop()-headerView.getHeight()));
        }
        else
        {
            c.translate(0,child.getTop()-headerView.getHeight());
        }
        headerView.draw(c);
        c.restore();
    }

    private void fixLayoutSize(View headerView, ViewGroup parent)
    {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), headerView.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingBottom() + parent.getPaddingTop(), headerView.getLayoutParams().height);

        headerView.measure(childWidth, childHeight);
        headerView.layout(0, 0 , headerView.getMeasuredWidth(), headerView.getMeasuredHeight());
    }
}