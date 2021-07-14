package com.example.stepgraph

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.stepgraph.databinding.FragmentFirstBinding
import com.github.mikephil.charting.charts.PieChart

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment() : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    lateinit var pieChartHandler: PieChartHandler
    var stepGoalStr: String? = null
    val debugTag = "FirstFragment_DEBUG"
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        pieChartHandler = PieChartHandler(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        on click listener for "Next" button that navigates to the second fragment
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        loadSettings()

        val stepGoal = stepGoalStr!!.toFloat()

        pieChartHandler.generatePieData(2500.0f, stepGoal)
        pieChartHandler.triggerAnimate()

    }

    fun loadSettings() {
        val preferenceManager = PreferenceManager.getDefaultSharedPreferences(context)

        stepGoalStr = preferenceManager.getString("step_goal", "10000")
    }

    fun goToNextView() {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}