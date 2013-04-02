/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ibhh.AnimalShop;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Simon
 */
public class MetricsHandler implements Serializable {

    private AnimalShop plugin;
    private Metrics metrics;
    public static HashMap<MTLocation, String> Shop = new HashMap<MTLocation, String>();
    public static int Error = 0;
    public int AnimalShopSignBuy = 0;
    public int Commands = 0;

    public MetricsHandler(AnimalShop pl) {
        plugin = pl;
    }

    public void onStart() {
        try {
            metrics = new Metrics(plugin);
        } catch (IOException ex) {
            plugin.Logger("There was an error while submitting statistics.", "Error");
        }
        initializeGraphs();
        startStatistics();
    }

    public void saveStatsFiles() {
        try {
            ObjectManager.save(Shop, plugin.getDataFolder() + File.separator + "metrics" + File.separator + "Shop.statistics");
            plugin.Logger("Shops stats file contains " + calculateShopQuantity() + " values!", "Debug");
        } catch (Exception e) {
            plugin.Logger("Cannot save Shop statistics!", "Error");
        }
    }

    public void loadStatsFiles() {
        try {
            Shop = ObjectManager.load(plugin.getDataFolder() + File.separator + "metrics" + File.separator + "Shop.statistics");
            plugin.Logger("Shops stats file contains " + calculateShopQuantity() + " values!", "Debug");
            plugin.Logger("Stats loaded!", "Debug");
        } catch (Exception e) {
            plugin.Logger("Cannot load Shop statistics!", "Error");
        }
    }

    private void startStatistics() {
        try {
            metrics.start();
        } catch (Exception ex) {
            plugin.Logger("There was an error while submitting statistics.", "Error");
        }
    }

    private void initializeGraphs() {
        initializeOthers();
        initializeDependenciesGraph();
        initializeCommandGraph();
    }

    public void initializeOthers() {
        Metrics.Graph ShopCountGraph = metrics.createGraph("Shops");
        ShopCountGraph.addPlotter(new Metrics.Plotter("AnimalShopSigns") {

            @Override
            public int getValue() {
                return calculateShopQuantity();
            }
        });
        Metrics.Graph GMGraph = metrics.createGraph("DefaultGameMode");
        GMGraph.addPlotter(new Metrics.Plotter(plugin.getServer().getDefaultGameMode().name()) {

            @Override
            public int getValue() {
                return 1;
            }
        });
        Metrics.Graph errorgraph = metrics.createGraph("uncatchedErrors");
        errorgraph.addPlotter(new Metrics.Plotter(plugin.getServer().getDefaultGameMode().name()) {

            @Override
            public int getValue() {
                return Error;
            }

            @Override
            public void reset() {
                Error = 0;
            }
        });
    }

    private void initializeCommandGraph() {
        Metrics.Graph CMDUses = metrics.createGraph("ShopUses");
        CMDUses.addPlotter(new Metrics.Plotter("BookShopSignBuy") {

            @Override
            public int getValue() {
                return AnimalShopSignBuy;
            }

            @Override
            public void reset() {
                AnimalShopSignBuy = 0;
            }
        });
        CMDUses.addPlotter(new Metrics.Plotter("Commands") {

            @Override
            public int getValue() {
                return Commands;
            }

            @Override
            public void reset() {
                Commands = 0;
            }
        });
    }

    public void initializeDependenciesGraph() {
        Metrics.Graph depGraph = metrics.createGraph("EconomyDependencies");
        String iConomyName = "None";
        if (plugin.MoneyHandler.iConomyversion() != 0) {
            if (plugin.MoneyHandler.iConomyversion() == 1) {
                iConomyName = "Register";
            } else if (plugin.MoneyHandler.iConomyversion() == 2) {
                iConomyName = "Vault";
            } else if (plugin.MoneyHandler.iConomyversion() == 5) {
                iConomyName = "iConomy5";
            } else if (plugin.MoneyHandler.iConomyversion() == 6) {
                iConomyName = "iConomy6";
            }
        }
        depGraph.addPlotter(new Metrics.Plotter(iConomyName) {

            @Override
            public int getValue() {
                return 1;
            }
        });
        Metrics.Graph Permgraph = metrics.createGraph("PermissionDependencies");
        String PermName = "BukkitPermissions";
        Permgraph.addPlotter(new Metrics.Plotter(PermName) {

            @Override
            public int getValue() {
                return 1;
            }
        });
    }

    public int calculateShopQuantity() {
        int a = 0;
        for (String i : Shop.values()) {
            a++;
        }
        return a;
    }
}
