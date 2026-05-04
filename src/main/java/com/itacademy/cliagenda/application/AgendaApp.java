package com.itacademy.cliagenda.application;

import com.itacademy.cliagenda.application.menu.AppMenu;

/**
 * Punto de entrada de la aplicación CLI-Agenda.
 * Inicia el menú principal de la aplicación de consola.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class AgendaApp {

    public static void main(String[] args) {

        AppMenu appMenu = new AppMenu();

        appMenu.playMenu();
    }
}
