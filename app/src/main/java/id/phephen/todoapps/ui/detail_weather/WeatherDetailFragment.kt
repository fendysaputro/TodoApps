package id.phephen.todoapps.ui.detail_weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.phephen.todoapps.R
import id.phephen.todoapps.data.model.Weather
import id.phephen.todoapps.databinding.FragmentWeatherDetailBinding
import id.phephen.todoapps.util.exhaustive
import kotlinx.android.synthetic.main.fragment_weather_detail.*
import kotlinx.coroutines.ensureActive

@AndroidEntryPoint
class WeatherDetailFragment : Fragment(R.layout.fragment_weather_detail) {
    private val viewModel: WeatherDetailViewModel by viewModels()
//    private var binding: FragmentWeatherDetailBinding? = null
    val argsData: WeatherDetailFragmentArgs by navArgs()

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWeatherBy(argsData.dateArg)
        val binding = FragmentWeatherDetailBinding.bind(view)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { state ->
                when (state.isLoading) {
                    true -> binding?.progressBar?.visibility = View.VISIBLE
                    false -> binding?.progressBar?.visibility = View.GONE
                }

                when (state.weather != null) {
                    true -> updateUI(state.weather)
                }

                when (state.error.isNotEmpty()) {
                    true -> Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                }
            }
        }.exhaustive
    }

    private fun updateUI(weather: Weather) {
        title?.text = weather.title
        description?.text = weather.description
        temperatureValue?.text = when {
            weather.day > 25 -> "Hot"
            weather.day < 10 -> "Cold"
            else -> "Normal"
        }
        dayValue?.text = weather.day.toString() + "°C"
        nightValue?.text = weather.night.toString() + "°C"
    }
}