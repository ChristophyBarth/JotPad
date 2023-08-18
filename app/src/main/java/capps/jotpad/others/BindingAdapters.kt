package capps.jotpad.others

import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.BindingAdapter

@BindingAdapter("animateFAB")
fun animateFAB(v: View, showFAB: Boolean) {
    if (!showFAB) {
        v.animation = AnimationUtils.loadAnimation(v.context, android.R.anim.slide_out_right)
    }
}