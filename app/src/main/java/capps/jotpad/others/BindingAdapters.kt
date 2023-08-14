package capps.jotpad.others

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("bottomSheetBehaviourState")
    fun setState(v: View, bottomSheetBehaviourState: Int) {
        val viewBottomSheetBehaviour = BottomSheetBehavior.from(v)
        viewBottomSheetBehaviour.state = bottomSheetBehaviourState
    }
}