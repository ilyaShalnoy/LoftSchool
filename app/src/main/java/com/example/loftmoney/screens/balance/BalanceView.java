package com.example.loftmoney.screens.balance;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.loftmoney.R;

public class BalanceView extends View {

    private float expenses = 5400;
    private float incomes = 7000;

    private Paint expensePaint = new Paint();
    private Paint incomePaint = new Paint();

    public BalanceView(Context context) {
        super(context);
        init(null);
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void update(float expenses, float incomes) {
        this.expenses = expenses;
        this.incomes = incomes;

        invalidate();
    }

    private void init(AttributeSet attrs) {
        //expensePaint.setColor(ContextCompat.getColor(getContext(), R.color.priceColor));
        // incomePaint.setColor(ContextCompat.getColor(getContext(), R.color.priceColor_2));

        TypedArray att = getContext().obtainStyledAttributes(attrs, R.styleable.BalanceView);
        expensePaint.setColor(att.getColor(R.styleable.BalanceView_expenseColor, getResources().getColor(R.color.priceColor)));
        incomePaint.setColor(att.getColor(R.styleable.BalanceView_incomeColor, getResources().getColor(R.color.priceColor_2)));
        att.recycle();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float total = expenses + incomes;

        float expenseAngle = 360f * expenses / total;
        float incomesAngle = 360f * incomes / total;

        int space = 20;
        int size = Math.min(getWidth(), getHeight()) - space * 2;
        int xMargin = (getWidth() - size) / 2;
        int yMargin = (getHeight() - size) / 2;

        canvas.drawArc(xMargin - space, yMargin,
                getWidth() - xMargin - space,
                getHeight() - yMargin, (180 - expenseAngle / 2),
                expenseAngle,
                true, expensePaint);

        canvas.drawArc(xMargin + space, yMargin,
                getWidth() - xMargin + space,
                getHeight() - yMargin,
                360 - incomesAngle / 2,
                incomesAngle,
                true, incomePaint);

    }
}
