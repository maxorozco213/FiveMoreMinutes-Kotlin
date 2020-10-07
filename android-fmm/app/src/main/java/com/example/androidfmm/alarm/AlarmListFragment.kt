package com.example.androidfmm.alarm

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfmm.R
import com.example.androidfmm.data.AlarmViewModel
import kotlinx.android.synthetic.main.fragment_alarm_list.*
import kotlinx.android.synthetic.main.fragment_alarm_list.view.*

class AlarmListFragment : Fragment() {
    private lateinit var mAlarmViewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val binding = DataBindingUtil.inflate<FragmentAlarmListBinding>(
//            inflater, R.layout.fragment_alarm_list, container, false)
//        binding.alarmList = this
        val view = inflater.inflate(R.layout.fragment_alarm_list, container, false)
        mAlarmViewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)

        val adapter = AlarmListAdapter(object:AlarmListAdapter.ItemSelectedListener {
            override fun onItemSelected(item:Any) {
                // Cast the item of type Any to an AlarmItem
                val alarm = item as AlarmItem
                // Toggle the boolean and call update on the item
                alarm.isActive = !alarm.isActive
                mAlarmViewModel.updateAlarm(alarm)
                Log.i("DATA", "isActive set to ${alarm.isActive}")
            }
        })

        val recyclerView = view.alarm_list_fragment

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mAlarmViewModel.readAllData.observe(viewLifecycleOwner, Observer { alarm ->
            adapter.setData(alarm)
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        val alarmItemsList = listOf(
//        )
//        alarm_list_fragment.adapter = AlarmListAdapter(alarmItemsList)

        alarm_list_fragment.layoutManager = LinearLayoutManager(requireContext())
        alarm_list_fragment.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.nav_create_new_alarm -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.createAlarmFragment)
            Log.i("MENU", "MENU NAV CLICKED")
            true
        }
        R.id.nav_settings -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.settings_menu)
            true
        }
        else -> {
            Log.i("MENU", "WAT")
            super.onOptionsItemSelected(item)
        }
    }
}