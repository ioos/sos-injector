package com.axiomalaska.sos;

import com.axiomalaska.phenomena.Phenomena;
import com.axiomalaska.phenomena.Phenomenon;

/**
 * This class builds the default Phenomena
 * 
 * @author Lance Finfrock
 */
public class PhenomenaBuilder {
    public @interface UnitsChanged {
        String newUnit();
        String oldUnit();
    }

	@Deprecated
	public Phenomenon createAirPressure() {
        return Phenomena.AIR_PRESSURE;
	}

	@Deprecated
	public Phenomenon createAirTemperature() {
        return Phenomena.AIR_TEMPERATURE;
    }

    @Deprecated
	public Phenomenon createAirVisibility() {
        return Phenomena.VISIBILITY_IN_AIR;
	}

	@Deprecated
	public Phenomenon createAltitude() {
        return Phenomena.ALTITUDE;
	}

	@Deprecated
	public Phenomenon createAverageAirTemperature() {
        return Phenomena.AIR_TEMPERATURE_AVERAGE;
	}

    @Deprecated
	public Phenomenon createAverageRelativeHumidity() {
        return Phenomena.RELATIVE_HUMIDITY_AVERAGE;
	}

    @Deprecated
	public Phenomenon createAverageSolarRadiation() {
        return Phenomena.SOLAR_RADIATION_AVERAGE;
	}

    @Deprecated
	public Phenomenon createAverageWavePeriod() {
	    return Phenomena.SEA_SURFACE_WAVE_MEAN_PERIOD;
	}

    @Deprecated
	public Phenomenon createBattery() {
        return Phenomena.BATTERY_VOLTAGE;
	}

    @Deprecated
    @UnitsChanged(oldUnit="mg/L",newUnit="kg/m³")
	public Phenomenon createCarbonDioxideConcentrationinAir() {
        return Phenomena.MASS_CONCENTRATION_OF_CARBON_DIOXIDE_IN_AIR;
	}

    @Deprecated
    @UnitsChanged(oldUnit="mg/L",newUnit="kg/m³")
	public Phenomenon createCarbonDioxideConcentrationinSeaWater() {
        return Phenomena.MASS_CONCENTRATION_OF_CARBON_DIOXIDE_IN_SEA_WATER;
	}

    @Deprecated
    @UnitsChanged(oldUnit="µg/L",newUnit="kg/m³")
	public Phenomenon createConcentrationofChlorophyllinSeaWater() {
        return Phenomena.MASS_CONCENTRATION_OF_CHLOROPHYLL_IN_SEA_WATER;
    }

    @Deprecated
	public Phenomenon createConcentrationofOxygeninSeaWater() {
        return Phenomena.MASS_CONCENTRATION_OF_OXYGEN_IN_SEA_WATER;
	}

    @Deprecated
	public Phenomenon createDepthtoWaterLevel() {
        return Phenomena.DEPTH_TO_WATER_LEVEL;
	}

    @Deprecated
    public Phenomenon createDewPointTemperature() {
        return Phenomena.DEW_POINT_TEMPERATURE;
	}

    @Deprecated
	public Phenomenon createDirectionofSeaWaterVelocity() {
        return Phenomena.DIRECTION_OF_SEA_WATER_VELOCITY;
	}

    @Deprecated
	public Phenomenon createDominantWavePeriod() {
		return Phenomena.DOMINANT_WAVE_PERIOD;
	}

    @Deprecated
	public Phenomenon createElevationofReservoirWaterSurfaceaboveDatum() {
        return Phenomena.WATER_SURFACE_HEIGHT_ABOVE_REFERENCE_DATUM;
	}

    @Deprecated
	public Phenomenon createFuelMoisture() {
        return Phenomena.FUEL_MOISTURE;
	}

    @Deprecated
	public Phenomenon createFuelTemperature() {
        return Phenomena.FUEL_TEMPERATURE;
	}

    @Deprecated
	public Phenomenon createGrideLongitude() {
        return Phenomena.GRID_LONGITUDE;
	}

    @Deprecated
	public Phenomenon createGridLatitude() {
        return Phenomena.GRID_LATITUDE;
	}

    @Deprecated
	public Phenomenon createGroundTemperature() {
        return Phenomena.SOIL_TEMPERATURE;
	}

    @Deprecated
	public Phenomenon createIncomingShortwaveRadiation() {
        return Phenomena.TOA_INCOMING_SHORTWAVE_FLUX;
	}

    @Deprecated
	public Phenomenon createMaximumAirTemperature() {
        return Phenomena.AIR_TEMPERATURE_MAXIMUM;
	}

    @Deprecated
	public Phenomenon createMaximumBattery() {
        return Phenomena.BATTERY_VOLTAGE_MAXIMUM;
	}

    @Deprecated
	public Phenomenon createMaximumRelativeHumidity() {
        return Phenomena.RELATIVE_HUMIDITY_MAXIMUM;
	}

    @Deprecated
	public Phenomenon createMaximumSolarRadiation() {
        return Phenomena.SOLAR_RADIATION_MAXIMUM;
	}

    @Deprecated
	public Phenomenon createMinimumAirTemperature() {
        return Phenomena.AIR_TEMPERATURE_MINIMUM;
	}

    @Deprecated
	public Phenomenon createMinimumBattery() {
        return Phenomena.BATTERY_VOLTAGE_MINIMUM;
	}

    @Deprecated
	public Phenomenon createMinimumRelativeHumidity() {
        return Phenomena.RELATIVE_HUMIDITY_MINIMUM;
	}

    @Deprecated
	public Phenomenon createMinimumSolarRadiation() {
        return Phenomena.SOLAR_RADIATION_MINIMUM;
	}

    @Deprecated
    @UnitsChanged(oldUnit="%",newUnit="") //dimensionless (fraction)
	public Phenomenon createOxygenSaturationinSeaWater() {
        return Phenomena.FRACTIONAL_SATURATION_OF_OXYGEN_IN_SEA_WATER;
	}

    @Deprecated
	public Phenomenon createPanelTemperature() {
        return Phenomena.PANEL_TEMPERATURE;
	}

    @Deprecated
	public Phenomenon createPhotosyntheticallyActiveRadiation() {
        return Phenomena.DOWNWELLING_PHOTOSYNTHETIC_RADIATIVE_FLUX_IN_SEA_WATER;
	}

    @Deprecated
	public Phenomenon createPhycoerythrin() {
        return Phenomena.PHYCOERYTHRIN;
	}

    @Deprecated
	public Phenomenon createPrecipitation() {
        return Phenomena.LWE_THICKNESS_OF_PRECIPITATION_AMOUNT;
	}

    @Deprecated
    @UnitsChanged(oldUnit="m",newUnit="mm")
	public Phenomenon createPrecipitationAccumulation() {
	    return Phenomena.PRECIPITATION_ACCUMULATED;
	}

    @Deprecated
	public Phenomenon createPrecipitationIncrement() {
        return Phenomena.PRECIPITATION_INCREMENT;
	}

    @Deprecated
	public Phenomenon createProductofAirTemperatureandSpecificHumidity() {
        return Phenomena.PRODUCT_OF_AIR_TEMPERATURE_AND_SPECIFIC_HUMIDITY;
    }

    @Deprecated
    @UnitsChanged(oldUnit="cm/s",newUnit="m/s")    
	public Phenomenon createRadialSeaWaterVelocityAwayFromInstrument() {
        return Phenomena.RADIAL_SEA_WATER_VELOCITY_AWAY_FROM_INSTRUMENT;
	}

    @Deprecated
	public Phenomenon createRealDielectricconstant() {
        return Phenomena.RELATIVE_PERMITTIVITY;
    }

    @Deprecated
	public Phenomenon createReflectedShortwaveRadiation() {
	    return Phenomena.TOA_OUTGOING_SHORTWAVE_FLUX;
	}

    @Deprecated
	public Phenomenon createRelativeHumidity() {
        return Phenomena.RELATIVE_HUMIDITY;
	}

    @Deprecated
	public Phenomenon createSeaFloorDepthBelowSeaSurface() {
        return Phenomena.SEA_FLOOR_DEPTH_BELOW_SEA_SURFACE;
    }

    @Deprecated
	public Phenomenon createseaseaSurfaceSwellWavePeriod() {
        return Phenomena.SEA_SURFACE_SWELL_WAVE_PERIOD;
	}

    @Deprecated
    @UnitsChanged(oldUnit="degree (from)",newUnit="degree (to)")  
	public Phenomenon createSeaSurfaceDominantWaveFromDirection() {
        return Phenomena.PEAK_WAVE_DIRECTION;
	}

    @Deprecated
	public Phenomenon createSeaSurfaceDominantWavePeriod() {
        return Phenomena.PEAK_WAVE_PERIOD;
	}

    @Deprecated
	public Phenomenon createSeaSurfaceHeightaboveSeaLevel() {
        return Phenomena.SEA_SURFACE_HEIGHT_ABOVE_SEA_LEVEL;
    }

    @Deprecated
	public Phenomenon createSeaSurfaceHeightaboveSeaLevelPredictions() {        
        return Phenomena.SEA_SURFACE_HEIGHT_ABOVE_SEA_LEVEL_PREDICTIONS;
	}

    @Deprecated
	public Phenomenon createSeaSurfaceMaximumWaveHeight() {
        return Phenomena.SEA_SURFACE_MAXIMUM_WAVE_HEIGHT;
    }

    @Deprecated
    @UnitsChanged(oldUnit="degree (from)",newUnit="degree (to)")    
	public Phenomenon createSeaSurfaceSwellWaveFromDirection() {
        return Phenomena.SEA_SURFACE_SWELL_WAVE_TO_DIRECTION;
	}

    @Deprecated
	public Phenomenon createseaSurfaceSwellWaveHeight() {
        return Phenomena.SEA_SURFACE_SWELL_WAVE_SIGNIFICANT_HEIGHT;
	}

    @Deprecated
	public Phenomenon createSeaSurfaceSwellWaveHeight() {
        return Phenomena.SEA_SURFACE_SWELL_WAVE_SIGNIFICANT_HEIGHT;
	}

    @Deprecated
    public Phenomenon createSeaSurfaceSwelWavePeriod() {
        return Phenomena.SEA_SURFACE_SWELL_WAVE_PERIOD;
    }

    @Deprecated
	public Phenomenon createSeaSurfaceWaveFromDirection() {
        return Phenomena.SEA_SURFACE_WAVE_FROM_DIRECTION;
	}

    @Deprecated
	public Phenomenon createSeaSurfaceWaveMeanHeight() {
        return Phenomena.SEA_SURFACE_WAVE_MEAN_HEIGHT;
    }

    @Deprecated
    public Phenomenon createSeaSurfaceWaveMeanPeriod() {
	    return Phenomena.SEA_SURFACE_WAVE_MEAN_PERIOD;
	}

    @Deprecated
	public Phenomenon createSeaSurfaceWaveSignificantHeight() {
		return Phenomena.SEA_SURFACE_WAVE_SIGNIFICANT_HEIGHT;
	}

    @Deprecated
    public Phenomenon createSeaSurfaceWaveSignificatHeight() {
        return Phenomena.SEA_SURFACE_WAVE_SIGNIFICANT_HEIGHT;
    }

    @Deprecated
    @UnitsChanged(oldUnit="degree (from)",newUnit="degree (to)")
	public Phenomenon createSeaSurfaceWindWaveDirection() {
        return Phenomena.SEA_SURFACE_WIND_WAVE_TO_DIRECTION;
	}

    @Deprecated
	public Phenomenon createSeaSurfaceWindWaveHeight() {
        return Phenomena.SEA_SURFACE_WIND_WAVE_SIGNIFICANT_HEIGHT;
	}

    @Deprecated
	public Phenomenon createSeaSurfaceWindWavePeriod() {
        return Phenomena.SEA_SURFACE_WIND_WAVE_PERIOD;
	}

    @Deprecated
	public Phenomenon createSeaWaterAcidity() {
        return Phenomena.SEA_WATER_PH_REPORTED_ON_TOTAL_SCALE;
    }

    @Deprecated
    @UnitsChanged(oldUnit="mS/s",newUnit="S/m")
	public Phenomenon createSeaWaterElectricalConductivity() {
        return Phenomena.SEA_WATER_ELECTRICAL_CONDUCTIVITY;
	}

    @Deprecated
	public Phenomenon createSeaWaterSalinity() {
        return Phenomena.SEA_WATER_PRACTICAL_SALINITY;
	}

    @Deprecated
    @UnitsChanged(oldUnit="cm/s",newUnit="m/s")        
	public Phenomenon createSeaWaterspeed() {
        return Phenomena.SEA_WATER_SPEED;
	}

    @Deprecated
	public Phenomenon createSeaWaterTemperature() {
		return Phenomena.SEA_WATER_TEMPERATURE;
	}

    @Deprecated
	public Phenomenon createSimpleTurbidity() {
        return Phenomena.TURBIDITY;
	}

    @Deprecated
	public Phenomenon createSnowdepth() {
        return Phenomena.SNOW_DEPTH;
	}

    @Deprecated
	public Phenomenon createSnowPillow() {
        return Phenomena.SNOW_PILLOW;
	}

    @Deprecated
	public Phenomenon createSnowWaterEquivalent() {
        return Phenomena.SNOW_WATER_EQUIVALENT;
	}

    @Deprecated
	public Phenomenon createSoilMoisturePercent() {
        return Phenomena.SOIL_MOISTURE_PERCENT;
	}

    @Deprecated
	public Phenomenon createSolarRadiation() {
        return Phenomena.SOLAR_RADIATION;
	}

    @Deprecated
    public Phenomenon createStreamflow() {
        return Phenomena.RIVER_DISCHARGE;
	}

    @Deprecated
    public Phenomenon createStreamGageHeight() {
        return Phenomena.HEIGHT;
    }

    @Deprecated
	public Phenomenon createWaterTemperatureIntragravel() {
        return Phenomena.WATER_TEMPERATURE_INTRAGRAVEL;
	}

    @Deprecated
	public Phenomenon createWaterVolumeTransportintoSeaWaterfromRivers() {
        return Phenomena.WATER_VOLUME_TRANSPORT_INTO_SEA_WATER_FROM_RIVERS;
	}

    @Deprecated
	public Phenomenon createWaveDirectionSpread() {
        return Phenomena.WAVE_DIRECTIONAL_SPREAD;
    }

    @Deprecated
	public Phenomenon createWebcam() {
	    return Phenomena.WEBCAM;
	}

    @Deprecated
	public Phenomenon createWindfromDirection() {
        return Phenomena.WIND_FROM_DIRECTION;
	}

    @Deprecated
	public Phenomenon createWindGeneratorCurrent() {
        return Phenomena.WIND_GENERATOR_CURRENT;
	}

    @Deprecated
	public Phenomenon createWindGustfromDirection() {
        return Phenomena.WIND_GUST_FROM_DIRECTION;
	}

    @Deprecated
    @UnitsChanged(oldUnit="cm/s",newUnit="m/s")
	public Phenomenon createWindSpeed() {
        return Phenomena.WIND_SPEED;
	}

    @Deprecated
    @UnitsChanged(oldUnit="cm/s",newUnit="m/s")
	public Phenomenon createWindSpeedofGust() {
        return Phenomena.WIND_SPEED_OF_GUST;
	}

    @Deprecated
    @UnitsChanged(oldUnit="cm/s",newUnit="m/s")
	public Phenomenon createWindVerticalVelocity() {
        return Phenomena.WIND_VERTICAL_VELOCITY;
	}
}
