package id.phephen.todoapps.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.phephen.todoapps.R
import id.phephen.todoapps.data.model.Weather
import id.phephen.todoapps.databinding.FragmentWeatherBinding
import id.phephen.todoapps.util.exhaustive

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val viewModel: WeatherListViewModel by viewModels()
    private val adapter: WeatherAdapter by lazy { WeatherAdapter(layoutInflater, ::onWeatherClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWeatherBinding.bind(view)
        binding.recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state.isLoading) {
                    true -> binding.progressBar.visibility = View.VISIBLE
                    false -> binding.progressBar.visibility = View.GONE
                }

                when (state.weatherList.isNotEmpty()) {
                    true -> adapter.add(state.weatherList)
                }

                when (state.error.isNotEmpty()) {
                    true -> Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                }
            }
        }.exhaustive
    }

    private fun onWeatherClick(weather: Weather) {
        val action = WeatherFragmentDirections.actionWeatherFragmentToWeatherDetailFragment(weather.dt)
        findNavController().navigate(action)
    }

}