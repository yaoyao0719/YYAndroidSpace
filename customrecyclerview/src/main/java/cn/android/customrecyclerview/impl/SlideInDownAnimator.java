package cn.android.customrecyclerview.impl;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.android.customrecyclerview.base.BaseItemAnimator;


/**
 * Created by yaoyao on 16/7/6.
 */
public class SlideInDownAnimator extends BaseItemAnimator {
    @Override
    protected void onPreAnimateAdd(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationY(holder.itemView, -holder.itemView.getHeight());
        ViewCompat.setAlpha(holder.itemView, 0);
    }

    @Override
    protected void onPreAnimateRemove(RecyclerView.ViewHolder holder) {

    }

    @Override
    protected AnimatorSet generateRemoveAnimator(RecyclerView.ViewHolder holder) {
        View target = holder.itemView;
        AnimatorSet animator = new AnimatorSet();
        animator.playTogether(
                ObjectAnimator.ofFloat(target, "translationY", 0, -holder.itemView.getHeight())
        );

        animator.setTarget(target);
        animator.setDuration(getRemoveDuration());

        return animator;
    }

    @Override
    protected AnimatorSet generateAddAnimator(RecyclerView.ViewHolder holder) {
        View target = holder.itemView;

        AnimatorSet animator = new AnimatorSet();

        animator.playTogether(
                ObjectAnimator.ofFloat(target, "translationY",0)
        );

        animator.setTarget(target);
        animator.setDuration(getAddDuration());

        return animator;
    }
}

