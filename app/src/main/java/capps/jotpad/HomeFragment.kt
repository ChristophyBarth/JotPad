package capps.jotpad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import capps.jotpad.adapter.NoteAdapter
import capps.jotpad.databinding.FragmentHomeBinding
import capps.jotpad.room.NoteDatabase
import capps.jotpad.room.NoteRepository
import capps.jotpad.viewmodel.HomeFragVM
import capps.jotpad.viewmodelfactory.HomeFragVMFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeFragVM: HomeFragVM
    private lateinit var noteAdapter: NoteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)

        val dao = NoteDatabase.getInstance(requireContext()).noteDAO
        val repository = NoteRepository(dao)
        val factory = HomeFragVMFactory(repository)

        homeFragVM = ViewModelProvider(this, factory)[HomeFragVM::class.java]
        binding.homeViewModel = homeFragVM
        binding.lifecycleOwner = viewLifecycleOwner

        init()

        homeFragVM.notes.observe(viewLifecycleOwner) { noteList ->
            noteAdapter.setList(noteList.sortedBy {
                it.lastUpdated
            }.reversed())
        }

        binding.newNote.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("editing", false)
            findNavController().navigate(R.id.action_homeFragment_to_editFragment, bundle)
        }
        return binding.root
    }

    private fun init() {
        binding.noteRecyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        noteAdapter = NoteAdapter(requireContext())
        binding.noteRecyclerView.adapter = noteAdapter
    }

}