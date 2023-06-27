package com.github.liberatemetumortis.emkoth

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Koth(val name: String, val time: String) {
    companion object {
        val koths = mutableListOf<Koth>()
        fun getKothsFromConfig(config: ConfigurationSection) {
            for(key in config.getKeys(false)) {
                val name = config.getString("$key.name") ?: continue
                val time = config.getString("$key.time") ?: continue
                koths.add(Koth(name, time))
            }
        }
    }

    fun start() {
        val timeZone = "Europe/Istanbul"
        val formatter = DateTimeFormatter.ofPattern("H:mm")
        val localTime = LocalTime.parse(this.time, formatter)
        var targetTime = ZonedDateTime.of(LocalDate.now(), localTime, ZoneId.of(timeZone))
        if (targetTime.isBefore(ZonedDateTime.now())) {
            targetTime = targetTime.plusDays(1)
        }
        val millis = targetTime.toInstant().toEpochMilli() - ZonedDateTime.now().toInstant().toEpochMilli()
        Bukkit.getScheduler().runTaskTimer(
            EMKoth.instance,
            Runnable {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "koth start ${this.name} 180")
            },
            millis / 50,
            20 * 60 * 60 * 24
        )
        Bukkit.getScheduler().runTaskTimer(
            EMKoth.instance,
            Runnable {
                val leftTime = PlaceholderAPI.setPlaceholders(null, "%koth_time_left%")
                if (leftTime == "N/A") return@Runnable
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "koth end ${this.name}")
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "koth start ${this.name} 120")

            },
            millis / 50 + 20 * 60 * 15,
            20 * 60 * 60 * 24
        )
    }
}