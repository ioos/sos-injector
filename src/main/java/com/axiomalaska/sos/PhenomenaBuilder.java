package com.axiomalaska.sos;

import com.axiomalaska.sos.data.Phenomenon;

public class PhenomenaBuilder {

	public Phenomenon createSeaWaterTemperature() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_water_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Sea Water Temperature");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceWaveSignificantHeight() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_significant_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Wave Significat Height");

		return phenomenon;
	}

	public Phenomenon createDominantWavePeriod() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_dominant_wave_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Dominant Wave Period");

		return phenomenon;
	}

	public Phenomenon createAverageWavePeriod() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_mean_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Wave Mean Period");

		return phenomenon;
	}

	public Phenomenon createseaSurfaceSwellWaveHeight() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_swell_wave_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Swell Wave Height");

		return phenomenon;
	}

	public Phenomenon createseaseaSurfaceSwellWavePeriod() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_swell_wave_period");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Swel Wave Period");

		return phenomenon;
	}

	public Phenomenon createElevationofReservoirWaterSurfaceaboveDatum() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/elevation_of_reservoir_water_surface_above_datum");
		phenomenon.setUnits("m");
		phenomenon.setName("Elevation of Reservoir Water Surface above Datum");

		return phenomenon;
	}

	public Phenomenon createWebcam() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/webcam");
		phenomenon.setUnits("image");
		phenomenon.setName("Webcam");

		return phenomenon;
	}

	public Phenomenon createReflectedShortwaveRadiation() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/reflected_shortwave_radiation");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Reflected Shortwave Radiation");

		return phenomenon;
	}

	public Phenomenon createIncomingShortwaveRadiation() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/incoming_shortwave_radiation");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Incoming Shortwave Radiation");

		return phenomenon;
	}

	public Phenomenon createPhotosyntheticallyActiveRadiation() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/photosynthetically_active_radiation");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Photosynthetically Active Radiation");

		return phenomenon;
	}

	public Phenomenon createWindGeneratorCurrent() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wind_generator_current");
		phenomenon.setUnits("A/hour");
		phenomenon.setName("Wind Generator Current");

		return phenomenon;
	}

	public Phenomenon createPanelTemperature() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/panel_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Panel Temperature");

		return phenomenon;
	}

	public Phenomenon createRealDielectricconstant() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/real_dielectric_constant");
		phenomenon.setUnits("uints");
		phenomenon.setName("Real Dielectric constant");

		return phenomenon;
	}

	public Phenomenon createFuelMoisture() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/fuel_moisture");
		phenomenon.setUnits("%");
		phenomenon.setName("Fuel Moisture");

		return phenomenon;
	}

	public Phenomenon createFuelTemperature() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/fuel_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Fuel Temperature");

		return phenomenon;
	}

	public Phenomenon createStreamflow() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/streamflow");
		phenomenon.setUnits("cfs");
		phenomenon.setName("Streamflow");

		return phenomenon;
	}

	public Phenomenon createSoilMoisturePercent() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/soil_moisture_percent");
		phenomenon.setUnits("%");
		phenomenon.setName("Soil Moisture Percent");

		return phenomenon;
	}

	public Phenomenon createGroundTemperature() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/ground_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Ground Temperature");

		return phenomenon;
	}

	public Phenomenon createDepthtoWaterLevel() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/depth_to_water_level");
		phenomenon.setUnits("m");
		phenomenon.setName("Depth to Water Level");

		return phenomenon;
	}

	public Phenomenon createStreamGageHeight() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/stream_gage_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Stream Gage Height");

		return phenomenon;
	}

	public Phenomenon createAirPressure() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/air_pressure");
		phenomenon.setUnits("Pa");
		phenomenon.setName("Air Pressure");

		return phenomenon;
	}

	public Phenomenon createAirTemperature() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/air_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Air Temperature");

		return phenomenon;
	}

	public Phenomenon createAltitude() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/altitude");
		phenomenon.setUnits("m");
		phenomenon.setName("Altitude");

		return phenomenon;
	}

	public Phenomenon createConcentrationofChlorophyllinSeaWater() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/mass_concentration_of_chlorophyll_in_sea_water");
		phenomenon.setUnits("µg/L");
		phenomenon.setName("Concentration of Chlorophyll in Sea Water");

		return phenomenon;
	}

	public Phenomenon createSeaWaterElectricalConductivity() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_water_electrical_conductivity");
		phenomenon.setUnits("mS/m");
		phenomenon.setName("Sea Water Electrical Conductivity");

		return phenomenon;
	}

	public Phenomenon createRadialSeaWaterVelocityAwayFromInstrument() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/radial_sea_water_velocity_away_from_instrument");
		phenomenon.setUnits("cm/s");
		phenomenon.setName("Radial Sea Water Velocity Away From Instrument");

		return phenomenon;
	}

	public Phenomenon createDirectionofSeaWaterVelocity() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/direction_of_sea_water_velocity");
		phenomenon.setUnits("degree");
		phenomenon.setName("Direction of Sea Water Velocity");

		return phenomenon;
	}

	public Phenomenon createSeaWaterspeed() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/sea_water_speed");
		phenomenon.setUnits("cm/s");
		phenomenon.setName("Sea Water speed");

		return phenomenon;
	}

	public Phenomenon createDewPointTemperature() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/dew_point_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Dew Point Temperature");

		return phenomenon;
	}

	public Phenomenon createWaterVolumeTransportintoSeaWaterfromRivers() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/water_volume_transport_into_sea_water_from_rivers");
		phenomenon.setUnits("m3/s");
		phenomenon.setName("Water Volume Transport into Sea Water from Rivers");

		return phenomenon;
	}

	public Phenomenon createGridLatitude() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/grid_latitude");
		phenomenon.setUnits("degree");
		phenomenon.setName("Grid Latitude");

		return phenomenon;
	}

	public Phenomenon createGrideLongitude() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/grid_longitude");
		phenomenon.setUnits("degree");
		phenomenon.setName("Gride Longitude");

		return phenomenon;
	}

	public Phenomenon createPhycoerythrin() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/phycoerythrin");
		phenomenon.setUnits("RFU");
		phenomenon.setName("Phycoerythrin");

		return phenomenon;
	}

	public Phenomenon createPrecipitation() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/precipitation");
		phenomenon.setUnits("m");
		phenomenon.setName("Precipitation");

		return phenomenon;
	}

	public Phenomenon createRelativeHumidity() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/relative_humidity");
		phenomenon.setUnits("%");
		phenomenon.setName("Relative Humidity");

		return phenomenon;
	}

	public Phenomenon createSeaWaterSalinity() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_water_salinity");
		phenomenon.setUnits("PSU");
		phenomenon.setName("Sea Water Salinity");

		return phenomenon;
	}

	public Phenomenon createSimpleTurbidity() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/simple_turbidity");
		phenomenon.setUnits("NTU");
		phenomenon.setName("Simple Turbidity");

		return phenomenon;
	}

	public Phenomenon createAirVisibility() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/visibility_in_air");
		phenomenon.setUnits("m");
		phenomenon.setName("Air Visibility");

		return phenomenon;
	}

	public Phenomenon createSeaFloorDepthBelowSeaSurface() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_floor_depth_below_sea_surface");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Floor Depth Below Sea Surface");

		return phenomenon;
	}

	public Phenomenon createWindfromDirection() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wind_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Wind from Direction");

		return phenomenon;
	}

	public Phenomenon createWindSpeed() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/wind_speed");
		phenomenon.setUnits("cm/s");
		phenomenon.setName("Wind Speed");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceWindWavePeriod() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wind_wave_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Wind Wave Period");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceSwelWavePeriod() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_swell_wave_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Swel Wave Period");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceWaveSignificatHeight() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_significant_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Wave Significat Height");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceWaveMeanHeight() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_mean_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Wave Mean Height");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceMaximumWaveHeight() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_maximum_wave_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Maximum Wave Height");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceWaveFromDirection() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Sea Surface Wave From Direction");

		return phenomenon;
	}

	public Phenomenon createWaveDirectionSpread() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wave_direction_spread");
		phenomenon.setUnits("degree");
		phenomenon.setName("Wave Direction Spread");

		return phenomenon;
	}

	public Phenomenon createProductofAirTemperatureandSpecificHumidity() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/product_of_air_temperature_and_specific_humidity");
		phenomenon.setUnits("C");
		phenomenon.setName("Product of Air Temperature and Specific Humidity");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceHeightaboveSeaLevel() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_height_above_sea_level");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Height above Sea Level");

		return phenomenon;
	}

	public Phenomenon createSeaWaterAcidity() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_water_acidity");
		phenomenon.setUnits("pH");
		phenomenon.setName("Sea Water Acidity");

		return phenomenon;
	}

	public Phenomenon createConcentrationofOxygeninSeaWater() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/mass_concentration_of_oxygen_in_sea_water ");
		phenomenon.setUnits("mg/L");
		phenomenon.setName("Concentration of Oxygen in Sea Water");

		return phenomenon;
	}

	public Phenomenon createAverageAirTemperature() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/air_temperature_average");
		phenomenon.setUnits("C");
		phenomenon.setName("Average Air Temperature");

		return phenomenon;
	}

	public Phenomenon createMaximumAirTemperature() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/air_temperature_maximum");
		phenomenon.setUnits("C");
		phenomenon.setName("Maximum Air Temperature");

		return phenomenon;
	}

	public Phenomenon createMinimumAirTemperature() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/air_temperature_minimum");
		phenomenon.setUnits("C");
		phenomenon.setName("Minimum Air Temperature");

		return phenomenon;
	}

	public Phenomenon createPrecipitationIncrement() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/precipitation_increment");
		phenomenon.setUnits("m");
		phenomenon.setName("Precipitation Increment");

		return phenomenon;
	}

	public Phenomenon createPrecipitationAccumulation() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/precipitation_accumulation");
		phenomenon.setUnits("m");
		phenomenon.setName("Precipitation Accumulation");

		return phenomenon;
	}

	public Phenomenon createMaximumRelativeHumidity() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/relative_humidity_maximum");
		phenomenon.setUnits("%");
		phenomenon.setName("Maximum Relative Humidity");

		return phenomenon;
	}

	public Phenomenon createMinimumRelativeHumidity() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/relative_humidity_minimum");
		phenomenon.setUnits("%");
		phenomenon.setName("Minimum Relative Humidity");

		return phenomenon;
	}

	public Phenomenon createAverageRelativeHumidity() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/relative_humidity_average");
		phenomenon.setUnits("%");
		phenomenon.setName("Average Relative Humidity");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceHeightaboveSeaLevelPredictions() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_height_above_sea_level_predictions");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Height above Sea Level Predictions");

		return phenomenon;
	}

	public Phenomenon createWaterTemperatureIntragravel() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/water_temperature_intragravel");
		phenomenon.setUnits("C");
		phenomenon.setName("Water Temperature Intragravel");

		return phenomenon;
	}

	public Phenomenon createWindVerticalVelocity() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wind_vertical_velocity");
		phenomenon.setUnits("cm/s");
		phenomenon.setName("Wind Vertical Velocity");

		return phenomenon;
	}

	public Phenomenon createWindGustfromDirection() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wind_gust_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Wind Gust from Direction");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceWaveMeanPeriod() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_mean_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Wave Mean Period");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceSwellWaveHeight() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_swell_wave_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Swell Wave Height");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceWindWaveHeight() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wind_wave_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Wind Wave Height");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceWindWaveDirection() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wind_wave_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Sea Surface Wind Wave Direction");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceSwellWaveFromDirection() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_swell_wave_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Sea Surface Swell Wave From Direction");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceDominantWaveFromDirection() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_dominant_wave_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Sea Surface Dominant Wave From Direction");

		return phenomenon;
	}

	public Phenomenon createSeaSurfaceDominantWavePeriod() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_dominant_wave_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Dominant Wave Period");

		return phenomenon;
	}

	public Phenomenon createBattery() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/battery");
		phenomenon.setUnits("V");
		phenomenon.setName("Battery");

		return phenomenon;
	}

	public Phenomenon createMaximumBattery() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/battery_maximum");
		phenomenon.setUnits("V");
		phenomenon.setName("Maximum Battery");

		return phenomenon;
	}

	public Phenomenon createMinimumBattery() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/battery_minimum");
		phenomenon.setUnits("V");
		phenomenon.setName("Minimum Battery");

		return phenomenon;
	}

	public Phenomenon createAverageSolarRadiation() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/solar_radiation_average");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Average Solar Radiation");

		return phenomenon;
	}

	public Phenomenon createSolarRadiation() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/solar_radiation");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Solar Radiation");

		return phenomenon;
	}

	public Phenomenon createMaximumSolarRadiation() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/solar_radiation_maximum");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Maximum Solar Radiation");

		return phenomenon;
	}

	public Phenomenon createMinimumSolarRadiation() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/solar_radiation_minimum");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Minimum Solar Radiation");

		return phenomenon;
	}

	public Phenomenon createOxygenSaturationinSeaWater() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/saturation_of_oxygen_in_sea_water");
		phenomenon.setUnits("%");
		phenomenon.setName("Oxygen Saturation in Sea Water");

		return phenomenon;
	}

	public Phenomenon createCarbonDioxideConcentrationinSeaWater() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/mass_concentration_of_carbon_dioxide_in_sea_water");
		phenomenon.setUnits("mg/L");
		phenomenon.setName("Carbon Dioxide Concentration in Sea Water");

		return phenomenon;
	}

	public Phenomenon createCarbonDioxideConcentrationinAir() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/mass_concentration_of_carbon_dioxide_in_air");
		phenomenon.setUnits("mg/L");
		phenomenon.setName("Carbon Dioxide Concentration in Air");

		return phenomenon;
	}

	public Phenomenon createSnowPillow() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/snow_pillow");
		phenomenon.setUnits("m");
		phenomenon.setName("Snow Pillow");

		return phenomenon;
	}

	public Phenomenon createSnowdepth() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/snow_depth");
		phenomenon.setUnits("m");
		phenomenon.setName("Snow depth");

		return phenomenon;
	}

	public Phenomenon createSnowWaterEquivalent() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/snow_water_equivalent");
		phenomenon.setUnits("m");
		phenomenon.setName("Snow Water Equivalent");

		return phenomenon;
	}

	public Phenomenon createWindSpeedofGust() {
		Phenomenon phenomenon = new Phenomenon();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wind_speed_of_gust");
		phenomenon.setUnits("cm/s");
		phenomenon.setName("Wind Speed of Gust");

		return phenomenon;
	}
}
