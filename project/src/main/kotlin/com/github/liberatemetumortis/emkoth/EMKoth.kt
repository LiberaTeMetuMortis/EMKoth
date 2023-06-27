package com.github.liberatemetumortis.emkoth

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class EMKoth : JavaPlugin() {
    companion object {
        lateinit var instance: EMKoth
    }
    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        reloadConfig()
        Koth.getKothsFromConfig(config.getConfigurationSection("koths")!!)
        Koth.koths.forEach(Koth::start)
    }

    override fun onDisable() {
        TODO("Plugin shutdown logic")
    }
}
