package capps.jotpad

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import capps.jotpad.databinding.FragmentEditBinding
import capps.jotpad.others.Object
import capps.jotpad.room.Note
import capps.jotpad.room.NoteDatabase
import capps.jotpad.room.NoteRepository
import capps.jotpad.viewmodel.EditFragVM
import capps.jotpad.viewmodelfactory.EditFragVMFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skydoves.colorpickerview.ActionMode
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener


class EditFragment : Fragment(), ColorEnvelopeListener {
    private lateinit var binding: FragmentEditBinding
    private lateinit var editFragVM: EditFragVM
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_edit, container, false)

        val editing = requireArguments().getBoolean("editing")
        val note = Object.MyGsonUtil().getGsonParser()
            .fromJson(requireArguments().getString("note"), Note::class.java)

        val dao = NoteDatabase.getInstance(requireContext()).noteDAO
        val repository = NoteRepository(dao)
        val factory = EditFragVMFactory(editing, note, repository)

        editFragVM = ViewModelProvider(this, factory)[EditFragVM::class.java]
        binding.myViewModel = editFragVM
        binding.lifecycleOwner = viewLifecycleOwner

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.isDraggable = false

        editFragVM.openBottomSheet.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled().let {
                binding.colorPickerView.setInitialColor(Color.parseColor(editFragVM.colorCode.value))
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                binding.fab.visibility = View.INVISIBLE
            }
        }
        editFragVM.closeBottomSheet.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled().let {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                binding.fab.visibility = View.VISIBLE
            }
        }

        editFragVM.statusMessage.observe(viewLifecycleOwner) { content ->
            content.getContentIfNotHandled().let { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }

        editFragVM.backButton.observe(viewLifecycleOwner){ content ->
            content.getContentIfNotHandled().let {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        editFragVM.colorCode.observe(viewLifecycleOwner) { color ->
            if (Object.isColorBright(color)) {
                binding.title.setTextColor(Color.BLACK)
                binding.body.setTextColor(Color.BLACK)
                binding.title.setHintTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.hint_light
                    )
                )
                binding.body.setHintTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.hint_light
                    )
                )
                binding.editing.setTextColor(Color.BLACK)
                Object.changeDrawableTintColor(
                    requireContext(), R.drawable.editing_txt_bg, R.color.black, binding.editing
                )

                val tintColor = ContextCompat.getColor(requireContext(), R.color.black)
                binding.backButton.setColorFilter(tintColor)
                binding.save.setColorFilter(tintColor)
            } else {
                binding.title.setTextColor(Color.WHITE)
                binding.body.setTextColor(Color.WHITE)
                binding.title.setHintTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.hint_dark
                    )
                )
                binding.body.setHintTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.hint_dark
                    )
                )
                binding.editing.setTextColor(Color.WHITE)
                Object.changeDrawableTintColor(
                    requireContext(), R.drawable.editing_txt_bg, R.color.white, binding.editing
                )

                val tintColor = ContextCompat.getColor(requireContext(), R.color.white)
                binding.backButton.setColorFilter(tintColor)
                binding.save.setColorFilter(tintColor)
            }

            requireActivity().window.statusBarColor = Color.parseColor(color)

            binding.root.setBackgroundColor(Color.parseColor(color))
            binding.appBar.setBackgroundColor(Color.parseColor(color))
        }

        binding.colorPickerView.attachBrightnessSlider(binding.brightnessSlide)
        binding.colorPickerView.setColorListener(this)

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    binding.fab.visibility = View.VISIBLE
                } else {
                    isEnabled = false
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })

        return binding.root
    }

    override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
        if (fromUser) {
            editFragVM.colorReturned("#${envelope?.hexCode}")
        }
    }
}