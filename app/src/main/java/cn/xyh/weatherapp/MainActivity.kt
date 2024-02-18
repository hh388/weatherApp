package cn.xyh.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import cn.xyh.weatherapp.ui.screen.WeatherCard
import cn.xyh.weatherapp.ui.screen.WeatherForecast
import cn.xyh.weatherapp.ui.theme.DarkBlue
import cn.xyh.weatherapp.ui.theme.DeepBlue
import cn.xyh.weatherapp.ui.theme.WeatherAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        setContent {
            WeatherAppTheme {
                val permissionsState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
                PermissionsRequired(
                    multiplePermissionsState = permissionsState,
                    permissionsNotGrantedContent = {
                        Box (
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Button(onClick = {
                                permissionsState.launchMultiplePermissionRequest()
                            }) {
                                Text(text = "点击获取权限")
                            }
                        }
                    },
                    permissionsNotAvailableContent = {
                        Box (
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                permissionsState.launchMultiplePermissionRequest()
                            }) {
                                Text(text = "点击获取权限")
                            }
                        }
                    }
                ) {
                    val viewModel: WeatherViewModel by viewModels()
                    // 获取Location的经纬度
                    val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location != null) {
                        viewModel.latitude = location.latitude
                        viewModel.longitude = location.longitude
                    }
                    viewModel.run {
                        loadWeatherInfo()
                    }
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(DarkBlue)
                        ) {
                            WeatherCard(
                                state = viewModel.state,
                                backgroundColor = DeepBlue
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            WeatherForecast(state = viewModel.state)

                            if (viewModel.state.isLoading) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}