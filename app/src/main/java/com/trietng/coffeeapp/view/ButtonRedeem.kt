package com.trietng.coffeeapp.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.trietng.coffeeapp.database.dao.VoucherExtra

class ButtonRedeem : AppCompatButton {

    constructor(context: Context) : super(context) {
        // ...
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // ...
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        // ...
    }

    var voucherExtra: VoucherExtra? = null
}