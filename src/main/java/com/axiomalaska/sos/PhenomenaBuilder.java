package com.axiomalaska.sos;

import com.axiomalaska.sos.data.SosPhenomenon;
import com.axiomalaska.sos.data.SosPhenomenonImp;

/**
 * This class builds the default Phenomena
 * 
 * @author Lance Finfrock
 */
public class PhenomenaBuilder {

	public SosPhenomenon createSeaWaterTemperature() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_water_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Sea Water Temperature");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceWaveSignificantHeight() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_significant_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Wave Significat Height");

		return phenomenon;
	}

	public SosPhenomenon createDominantWavePeriod() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_dominant_wave_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Dominant Wave Period");

		return phenomenon;
	}

	public SosPhenomenon createAverageWavePeriod() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_mean_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Wave Mean Period");

		return phenomenon;
	}

	public SosPhenomenon createseaSurfaceSwellWaveHeight() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_swell_wave_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Swell Wave Height");

		return phenomenon;
	}

	public SosPhenomenon createseaseaSurfaceSwellWavePeriod() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_swell_wave_period");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Swel Wave Period");

		return phenomenon;
	}

	public SosPhenomenon createElevationofReservoirWaterSurfaceaboveDatum() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/elevation_of_reservoir_water_surface_above_datum");
		phenomenon.setUnits("m");
		phenomenon.setName("Elevation of Reservoir Water Surface above Datum");

		return phenomenon;
	}

	public SosPhenomenon createWebcam() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/webcam");
		phenomenon.setUnits("image");
		phenomenon.setName("Webcam");

		return phenomenon;
	}

	public SosPhenomenon createReflectedShortwaveRadiation() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/reflected_shortwave_radiation");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Reflected Shortwave Radiation");

		return phenomenon;
	}

	public SosPhenomenon createIncomingShortwaveRadiation() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/incoming_shortwave_radiation");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Incoming Shortwave Radiation");

		return phenomenon;
	}

	public SosPhenomenon createPhotosyntheticallyActiveRadiation() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/photosynthetically_active_radiation");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Photosynthetically Active Radiation");

		return phenomenon;
	}

	public SosPhenomenon createWindGeneratorCurrent() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wind_generator_current");
		phenomenon.setUnits("A/hour");
		phenomenon.setName("Wind Generator Current");

		return phenomenon;
	}

	public SosPhenomenon createPanelTemperature() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/panel_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Panel Temperature");

		return phenomenon;
	}

	public SosPhenomenon createRealDielectricconstant() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/real_dielectric_constant");
		phenomenon.setUnits("uints");
		phenomenon.setName("Real Dielectric constant");

		return phenomenon;
	}

	public SosPhenomenon createFuelMoisture() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/fuel_moisture");
		phenomenon.setUnits("%");
		phenomenon.setName("Fuel Moisture");

		return phenomenon;
	}

	public SosPhenomenon createFuelTemperature() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/fuel_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Fuel Temperature");

		return phenomenon;
	}

	public SosPhenomenon createStreamflow() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/streamflow");
		phenomenon.setUnits("cfs");
		phenomenon.setName("Streamflow");

		return phenomenon;
	}

	public SosPhenomenon createSoilMoisturePercent() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/soil_moisture_percent");
		phenomenon.setUnits("%");
		phenomenon.setName("Soil Moisture Percent");

		return phenomenon;
	}

	public SosPhenomenon createGroundTemperature() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/ground_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Ground Temperature");

		return phenomenon;
	}

	public SosPhenomenon createDepthtoWaterLevel() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/depth_to_water_level");
		phenomenon.setUnits("m");
		phenomenon.setName("Depth to Water Level");

		return phenomenon;
	}

	public SosPhenomenon createStreamGageHeight() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/stream_gage_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Stream Gage Height");

		return phenomenon;
	}

	public SosPhenomenon createAirPressure() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/air_pressure");
		phenomenon.setUnits("Pa");
		phenomenon.setName("Air Pressure");

		return phenomenon;
	}

	public SosPhenomenon createAirTemperature() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/air_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Air Temperature");

		return phenomenon;
	}

	public SosPhenomenon createAltitude() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/altitude");
		phenomenon.setUnits("m");
		phenomenon.setName("Altitude");

		return phenomenon;
	}

	public SosPhenomenon createConcentrationofChlorophyllinSeaWater() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/mass_concentration_of_chlorophyll_in_sea_water");
		phenomenon.setUnits("µg/L");
		phenomenon.setName("Concentration of Chlorophyll in Sea Water");

		return phenomenon;
	}

	public SosPhenomenon createSeaWaterElectricalConductivity() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_water_electrical_conductivity");
		phenomenon.setUnits("mS/m");
		phenomenon.setName("Sea Water Electrical Conductivity");

		return phenomenon;
	}

	public SosPhenomenon createRadialSeaWaterVelocityAwayFromInstrument() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/radial_sea_water_velocity_away_from_instrument");
		phenomenon.setUnits("cm/s");
		phenomenon.setName("Radial Sea Water Velocity Away From Instrument");

		return phenomenon;
	}

	public SosPhenomenon createDirectionofSeaWaterVelocity() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/direction_of_sea_water_velocity");
		phenomenon.setUnits("degree");
		phenomenon.setName("Direction of Sea Water Velocity");

		return phenomenon;
	}

	public SosPhenomenon createSeaWaterspeed() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/sea_water_speed");
		phenomenon.setUnits("cm/s");
		phenomenon.setName("Sea Water speed");

		return phenomenon;
	}

	public SosPhenomenon createDewPointTemperature() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/dew_point_temperature");
		phenomenon.setUnits("C");
		phenomenon.setName("Dew Point Temperature");

		return phenomenon;
	}

	public SosPhenomenon createWaterVolumeTransportintoSeaWaterfromRivers() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/water_volume_transport_into_sea_water_from_rivers");
		phenomenon.setUnits("m3/s");
		phenomenon.setName("Water Volume Transport into Sea Water from Rivers");

		return phenomenon;
	}

	public SosPhenomenon createGridLatitude() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/grid_latitude");
		phenomenon.setUnits("degree");
		phenomenon.setName("Grid Latitude");

		return phenomenon;
	}

	public SosPhenomenon createGrideLongitude() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/grid_longitude");
		phenomenon.setUnits("degree");
		phenomenon.setName("Gride Longitude");

		return phenomenon;
	}

	public SosPhenomenon createPhycoerythrin() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/phycoerythrin");
		phenomenon.setUnits("RFU");
		phenomenon.setName("Phycoerythrin");

		return phenomenon;
	}

	public SosPhenomenon createPrecipitation() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/precipitation");
		phenomenon.setUnits("m");
		phenomenon.setName("Precipitation");

		return phenomenon;
	}

	public SosPhenomenon createRelativeHumidity() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/relative_humidity");
		phenomenon.setUnits("%");
		phenomenon.setName("Relative Humidity");

		return phenomenon;
	}

	public SosPhenomenon createSeaWaterSalinity() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_water_salinity");
		phenomenon.setUnits("PSU");
		phenomenon.setName("Sea Water Salinity");

		return phenomenon;
	}

	public SosPhenomenon createSimpleTurbidity() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/simple_turbidity");
		phenomenon.setUnits("NTU");
		phenomenon.setName("Simple Turbidity");

		return phenomenon;
	}

	public SosPhenomenon createAirVisibility() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/visibility_in_air");
		phenomenon.setUnits("m");
		phenomenon.setName("Air Visibility");

		return phenomenon;
	}

	public SosPhenomenon createSeaFloorDepthBelowSeaSurface() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_floor_depth_below_sea_surface");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Floor Depth Below Sea Surface");

		return phenomenon;
	}

	public SosPhenomenon createWindfromDirection() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wind_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Wind from Direction");

		return phenomenon;
	}

	public SosPhenomenon createWindSpeed() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/wind_speed");
		phenomenon.setUnits("cm/s");
		phenomenon.setName("Wind Speed");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceWindWavePeriod() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wind_wave_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Wind Wave Period");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceSwelWavePeriod() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_swell_wave_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Swel Wave Period");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceWaveSignificatHeight() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_significant_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Wave Significat Height");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceWaveMeanHeight() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_mean_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Wave Mean Height");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceMaximumWaveHeight() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_maximum_wave_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Maximum Wave Height");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceWaveFromDirection() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Sea Surface Wave From Direction");

		return phenomenon;
	}

	public SosPhenomenon createWaveDirectionSpread() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wave_direction_spread");
		phenomenon.setUnits("degree");
		phenomenon.setName("Wave Direction Spread");

		return phenomenon;
	}

	public SosPhenomenon createProductofAirTemperatureandSpecificHumidity() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/product_of_air_temperature_and_specific_humidity");
		phenomenon.setUnits("C");
		phenomenon.setName("Product of Air Temperature and Specific Humidity");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceHeightaboveSeaLevel() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_height_above_sea_level");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Height above Sea Level");

		return phenomenon;
	}

	public SosPhenomenon createSeaWaterAcidity() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_water_acidity");
		phenomenon.setUnits("pH");
		phenomenon.setName("Sea Water Acidity");

		return phenomenon;
	}

	public SosPhenomenon createConcentrationofOxygeninSeaWater() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/mass_concentration_of_oxygen_in_sea_water ");
		phenomenon.setUnits("mg/L");
		phenomenon.setName("Concentration of Oxygen in Sea Water");

		return phenomenon;
	}

	public SosPhenomenon createAverageAirTemperature() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/air_temperature_average");
		phenomenon.setUnits("C");
		phenomenon.setName("Average Air Temperature");

		return phenomenon;
	}

	public SosPhenomenon createMaximumAirTemperature() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/air_temperature_maximum");
		phenomenon.setUnits("C");
		phenomenon.setName("Maximum Air Temperature");

		return phenomenon;
	}

	public SosPhenomenon createMinimumAirTemperature() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/air_temperature_minimum");
		phenomenon.setUnits("C");
		phenomenon.setName("Minimum Air Temperature");

		return phenomenon;
	}

	public SosPhenomenon createPrecipitationIncrement() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/precipitation_increment");
		phenomenon.setUnits("m");
		phenomenon.setName("Precipitation Increment");

		return phenomenon;
	}

	public SosPhenomenon createPrecipitationAccumulation() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/precipitation_accumulation");
		phenomenon.setUnits("m");
		phenomenon.setName("Precipitation Accumulation");

		return phenomenon;
	}

	public SosPhenomenon createMaximumRelativeHumidity() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/relative_humidity_maximum");
		phenomenon.setUnits("%");
		phenomenon.setName("Maximum Relative Humidity");

		return phenomenon;
	}

	public SosPhenomenon createMinimumRelativeHumidity() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/relative_humidity_minimum");
		phenomenon.setUnits("%");
		phenomenon.setName("Minimum Relative Humidity");

		return phenomenon;
	}

	public SosPhenomenon createAverageRelativeHumidity() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/relative_humidity_average");
		phenomenon.setUnits("%");
		phenomenon.setName("Average Relative Humidity");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceHeightaboveSeaLevelPredictions() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_height_above_sea_level_predictions");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Height above Sea Level Predictions");

		return phenomenon;
	}

	public SosPhenomenon createWaterTemperatureIntragravel() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/water_temperature_intragravel");
		phenomenon.setUnits("C");
		phenomenon.setName("Water Temperature Intragravel");

		return phenomenon;
	}

	public SosPhenomenon createWindVerticalVelocity() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wind_vertical_velocity");
		phenomenon.setUnits("cm/s");
		phenomenon.setName("Wind Vertical Velocity");

		return phenomenon;
	}

	public SosPhenomenon createWindGustfromDirection() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wind_gust_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Wind Gust from Direction");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceWaveMeanPeriod() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wave_mean_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Wave Mean Period");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceSwellWaveHeight() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_swell_wave_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Swell Wave Height");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceWindWaveHeight() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wind_wave_height");
		phenomenon.setUnits("m");
		phenomenon.setName("Sea Surface Wind Wave Height");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceWindWaveDirection() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_wind_wave_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Sea Surface Wind Wave Direction");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceSwellWaveFromDirection() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_swell_wave_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Sea Surface Swell Wave From Direction");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceDominantWaveFromDirection() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_dominant_wave_from_direction");
		phenomenon.setUnits("degree");
		phenomenon.setName("Sea Surface Dominant Wave From Direction");

		return phenomenon;
	}

	public SosPhenomenon createSeaSurfaceDominantWavePeriod() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/sea_surface_dominant_wave_period");
		phenomenon.setUnits("s");
		phenomenon.setName("Sea Surface Dominant Wave Period");

		return phenomenon;
	}

	public SosPhenomenon createBattery() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/battery");
		phenomenon.setUnits("V");
		phenomenon.setName("Battery");

		return phenomenon;
	}

	public SosPhenomenon createMaximumBattery() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/battery_maximum");
		phenomenon.setUnits("V");
		phenomenon.setName("Maximum Battery");

		return phenomenon;
	}

	public SosPhenomenon createMinimumBattery() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/battery_minimum");
		phenomenon.setUnits("V");
		phenomenon.setName("Minimum Battery");

		return phenomenon;
	}

	public SosPhenomenon createAverageSolarRadiation() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/solar_radiation_average");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Average Solar Radiation");

		return phenomenon;
	}

	public SosPhenomenon createSolarRadiation() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/solar_radiation");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Solar Radiation");

		return phenomenon;
	}

	public SosPhenomenon createMaximumSolarRadiation() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/solar_radiation_maximum");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Maximum Solar Radiation");

		return phenomenon;
	}

	public SosPhenomenon createMinimumSolarRadiation() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/solar_radiation_minimum");
		phenomenon.setUnits("W/m2");
		phenomenon.setName("Minimum Solar Radiation");

		return phenomenon;
	}

	public SosPhenomenon createOxygenSaturationinSeaWater() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/saturation_of_oxygen_in_sea_water");
		phenomenon.setUnits("%");
		phenomenon.setName("Oxygen Saturation in Sea Water");

		return phenomenon;
	}

	public SosPhenomenon createCarbonDioxideConcentrationinSeaWater() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/mass_concentration_of_carbon_dioxide_in_sea_water");
		phenomenon.setUnits("mg/L");
		phenomenon.setName("Carbon Dioxide Concentration in Sea Water");

		return phenomenon;
	}

	public SosPhenomenon createCarbonDioxideConcentrationinAir() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/mass_concentration_of_carbon_dioxide_in_air");
		phenomenon.setUnits("mg/L");
		phenomenon.setName("Carbon Dioxide Concentration in Air");

		return phenomenon;
	}

	public SosPhenomenon createSnowPillow() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/snow_pillow");
		phenomenon.setUnits("m");
		phenomenon.setName("Snow Pillow");

		return phenomenon;
	}

	public SosPhenomenon createSnowdepth() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon.setId("http://mmisw.org/ont/ioos/parameter/snow_depth");
		phenomenon.setUnits("m");
		phenomenon.setName("Snow depth");

		return phenomenon;
	}

	public SosPhenomenon createSnowWaterEquivalent() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/snow_water_equivalent");
		phenomenon.setUnits("m");
		phenomenon.setName("Snow Water Equivalent");

		return phenomenon;
	}

	public SosPhenomenon createWindSpeedofGust() {
		SosPhenomenonImp phenomenon = new SosPhenomenonImp();

		phenomenon
				.setId("http://mmisw.org/ont/ioos/parameter/wind_speed_of_gust");
		phenomenon.setUnits("cm/s");
		phenomenon.setName("Wind Speed of Gust");

		return phenomenon;
	}
}
