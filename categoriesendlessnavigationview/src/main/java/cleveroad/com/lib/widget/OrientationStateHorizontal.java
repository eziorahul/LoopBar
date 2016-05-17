package cleveroad.com.lib.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.UnknownFormatFlagsException;

import cleveroad.com.lib.R;

class OrientationStateHorizontal extends AbstractOrientationState implements IOrientationState {

    private SpacesLeftItemDecoration itemDecoration = new SpacesLeftItemDecoration(0);

    OrientationStateHorizontal(@EndlessNavigationView.GravityAttr int gravityAttribute) {
        super(gravityAttribute);
    }

    @Override
    public LinearLayoutManager getLayoutManager(Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.enls_view_categories_navigation_horizontal;
    }

    @Override
    public boolean isItemsFitOnScreen(int containerWidth, int containerHeight, int itemWidth, int itemHeight, int itemsSize) {
        int itemsWidth = itemWidth * (itemsSize);
        return containerWidth >= itemsWidth;
    }

    @Override
    public AbstractSpacesItemDecoration getSelectionViewItemDecoration(int margin, int selectionViewWidth, int selectionViewHeight) {
        itemDecoration.setSpace(margin + selectionViewWidth);
        return itemDecoration;
    }

    @Override
    public int getOrientation() {
        return Orientation.ORIENTATION_HORIZONTAL;
    }

    @Override
    public void initSelectionContainer(ViewGroup selectionViewContainer) {
        selectionViewContainer.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        selectionViewContainer.requestLayout();
    }

    @Override
    public void initPlaceHolderAndOverlay(@Nullable View overlayPlaceHolder, RecyclerView rvCategories, int overlaySize) {
        if (overlayPlaceHolder != null) {
            //make placeholder same height as a recyclerView
            overlayPlaceHolder.getLayoutParams().height = rvCategories.getMeasuredHeight();
            overlayPlaceHolder.requestLayout();

            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) rvCategories.getLayoutParams();
            marginLayoutParams.setMargins(0, overlaySize, 0, 0);
            rvCategories.requestLayout();
        }
    }

    @Override
    protected ISelectionGravityState getGravityState(int gravityAttribute) {
        switch (gravityAttribute) {
            case EndlessNavigationView.SELECTION_GRAVITY_START:
                return new StartGravityState();
            case EndlessNavigationView.SELECTION_GRAVITY_END:
                return new EndGravityState();
            default:
                throw new UnknownFormatFlagsException("unknown gravity Attribute = " + gravityAttribute + ". Should be one of SELECTION_GRAVITY");
        }
    }

    static class StartGravityState implements ISelectionGravityState {

        @Override
        public int getSelectionGravity() {
            return Gravity.START;
        }

        @Override
        public <T extends ViewGroup.MarginLayoutParams> T setSelectionMargin(int marginPx, T layoutParams) {
            layoutParams.leftMargin = marginPx;
            return layoutParams;
        }
    }

    static class EndGravityState implements ISelectionGravityState {
        @Override
        public int getSelectionGravity() {
            return Gravity.END;
        }

        @Override
        public <T extends ViewGroup.MarginLayoutParams> T setSelectionMargin(int marginPx, T layoutParams) {
            layoutParams.rightMargin = marginPx;
            return layoutParams;
        }
    }
}
