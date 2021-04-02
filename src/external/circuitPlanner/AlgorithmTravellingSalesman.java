package external.circuitPlanner;

import optimodLyon.model.Node;
import optimodLyon.model.Pickup;
import optimodLyon.model.Warehouse;
import optimodLyon.model.circuit.Edge;
import optimodLyon.model.circuit.Graph;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Classe abstraite ayant pour objectif de définir la structure des classes résolvant le problème du voyageur de
 * commerce. Fournit des méthodes aidant ces classes à effectuer leurs calculs.
 */
public abstract class AlgorithmTravellingSalesman {

    /**
     * Contient tous les noeuds valides, propices à être ajoutés au path à un instant T, selon la contrainte qu'un
     * pickup doit forcément être avant un delivery d'une même requête.
     */
    protected ArrayList<Node> validNodes;

    /**
     * Tous les noeuds ayant été ajoutés au path final.
     */
    protected ArrayList<Node> selectedNodes;

    /**
     * Constructeur de la classe abstraite.
     */
    public AlgorithmTravellingSalesman() {
        this.validNodes = new ArrayList<>();
        this.selectedNodes = new ArrayList<>();
    }

    /**
     * Méthode qui prend en entrée un graphe complet contenant toutes les villes et leur chemin pour accéder à toutes
     * les autres villes du graphe, et retourne une solution au problème du voyageur de commerce pour aller d'une ville
     * à l'autre.
     * @param g Graphe complet.
     * @return Chemin le plus court.
     */
    public abstract Graph calculate(Graph g);

    /**
     * Ajoute un nouveau noeud à la liste de noeuds sélectionnés / faisant partie du path, à la fin de ce dit path.
     * Si le noeud est un pickup, ajoute le delivery correspondant à la liste de noeuds valides/sélectionnables.
     * @param n Noeud sélectionné.
     */
    protected void addNodeToSelected(Node n) {
        this.addNodeToSelected(n, this.selectedNodes.size());
    }

    /**
     * Ajoute un nouveau noeud à la liste de noeuds sélectionnés / faisant partie du path, à un index donné.
     * Si le noeud est un pickup, ajoute le delivery correspondant à la liste de noeuds valides/sélectionnables.
     * @param n Noeud sélectionné.
     * @param index Index auquel on veut ajouter le nouveau noeud.
     */
    protected void addNodeToSelected(Node n, int index) {
        validNodes.remove(n);
        selectedNodes.add(index, n);

        // If pickup, add delivery as valid node
        if (n instanceof Pickup) {
            // Get associated request, add delivery node in valid nodes list
            Pickup p = (Pickup) n;
            validNodes.add(p.getRequest().getDelivery());
        }
    }

    /**
     * Initialise la liste de noeuds sélectionnables en y ajoutant tous les pickups.
     * @param g Graphe.
     */
    protected void initValidNodes(Graph g) {
        for (Node n : g.getNodes()) {
            if (n instanceof Pickup) {
                validNodes.add(n);
            }
        }
    }

    /**
     * Retourne le noeud non sélectionné le plus proche d'un noeud donné.
     * @param g Graphe.
     * @param searched Noeud recherché.
     * @return Noeud non sélectionné le plus proche du noeud searched.
     */
    protected Map.Entry<Node, Float> getClosestNode(Graph g, Node searched) {
        float minimumLength = Float.MAX_VALUE;
        Node bestNode = null;

        for (Node potentialClosest : validNodes) {
            if (!selectedNodes.contains(potentialClosest)) {
                Edge e = g.getEdgeByNodes(searched, potentialClosest);
                if (e.getLength() < minimumLength) {
                    bestNode = potentialClosest;
                    minimumLength = e.getLength();
                }
            }
        }

        return new AbstractMap.SimpleEntry<>(bestNode, minimumLength);
    }

    /**
     * Retourne le noeud non sélectionné le plus éloigné d'un noeud donné.
     * @param g Graphe.
     * @param searched Noeud recherché.
     * @return Noeud non sélectionné le plus proche du noeud searched.
     */
    protected Map.Entry<Node, Float> getFarthestNode(Graph g, Node searched) {
        float maximumLength = Float.MIN_VALUE;
        Node bestNode = null;

        for (Node potentialFarthest : validNodes) {
            if (!selectedNodes.contains(potentialFarthest)) {
                Edge e = g.getEdgeByNodes(searched, potentialFarthest);
                if (e.getLength() > maximumLength) {
                    bestNode = potentialFarthest;
                    maximumLength = e.getLength();
                }
            }
        }

        return new AbstractMap.SimpleEntry<>(bestNode, maximumLength);
    }

    /**
     * Affiche le path final dans la console, avec la longueur de l'arrête l'y connectant.
     * @param g Graphe.
     */
    protected void displayResult(Graph g) {
        // Display nodes
        for (int i = 0; i < selectedNodes.size(); ++i) {
            int j = i+1;
            if (j == selectedNodes.size()) j = 0;
            String text = selectedNodes.get(i) + " : " + g.getEdgeByNodes(selectedNodes.get(i), selectedNodes.get(j)).getLength();
            System.out.println(text);
        }
    }

    /**
     * Vérifie si le path final respecte toutes les contraintes; soit, que pour toutes les requêtes, le pickup
     * est avant le delivery.
     * @param result Graphe que l'on souhaite tester.
     * @param printResult Permet de choisir si le résultat doit être affiché dans la console ou non.
     * @return Vrai si le path respecte toutes les contraintes, faux sinon.
     */
    protected boolean checkResultValidity(Graph result, boolean printResult) {
        boolean isValid = true;

        if (!(result.getFirstNode() instanceof Warehouse)) {
            isValid = false;
        }

        for (Node n : result.getNodes()) {
            if (n instanceof Pickup) {
                int pickupIndex = result.getNodes().indexOf(n);
                int deliveryIndex = result.getNodes().indexOf(((Pickup) n).getRequest().getDelivery());
                if (pickupIndex > deliveryIndex) {
                    isValid = false;
                }
            }
        }

        if (printResult) {
            if (!isValid) {
                System.err.println("Invalid result!");
            }
            else {
                System.out.println("The result is valid.");
            }
        }

        return isValid;
    }

    /**
     * Affiche les noeuds actuellement sélectionnés dans le path.
     */
    protected void displayPath() {
        System.out.println("Current path : ");
        for (Node selectedNode : selectedNodes) {
            System.out.println(selectedNode);
        }
    }

    /**
     * Affiche les noeuds sélectionnables dans le path.
     */
    protected void displayValidNodes() {
        System.out.println("Valid nodes :");
        for (Node validNode : validNodes) {
            System.out.println(validNode);
        }
    }
}
