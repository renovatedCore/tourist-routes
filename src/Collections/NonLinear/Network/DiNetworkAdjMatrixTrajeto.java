package Collections.NonLinear.Network;

import Collections.Exception.ElementNotFoundException;
import Collections.Linear.Interfaces.UnorderedListADT;
import Collections.Linear.List.UnorderedList.LinkedUnorderedList;
import Collections.NonLinear.Graph.matrix.adjMatrixDiGraph;
import TouristRoutes.Trajeto;


/**
 * Created by aluno on 1/19/16.
 */
public class DiNetworkAdjMatrixTrajeto<T> extends adjMatrixDiGraph<T> {

    private UnorderedListADT<Trajeto>[][] weightAdjMatrix;

    public DiNetworkAdjMatrixTrajeto() {
        super();
        this.weightAdjMatrix = new LinkedUnorderedList[this.getCapacity()][this.getCapacity()];

    }

    public DiNetworkAdjMatrixTrajeto(int capacity) {
        super(capacity);
        this.weightAdjMatrix = new LinkedUnorderedList[this.getCapacity()][this.getCapacity()];

    }

    /**
     * Creates new arrays to store the contents of the graph with twice the
     * capacity.
     */
    @Override
    protected void expandCapacity() {
        super.expandCapacity();
        UnorderedListADT<Trajeto>[][] largerWeightsMatrix = (new LinkedUnorderedList[this.getNumVertices() * 2][this.getNumVertices() * 2]);
        for (int i = 0; i < this.getNumVertices(); i++) {
            for (int j = 0; j < getNumVertices(); j++) {
                largerWeightsMatrix[i][j] = this.weightAdjMatrix[i][j];
            }
        }
        this.weightAdjMatrix = largerWeightsMatrix;
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the graph if
     * necessary. It also associates an object with the vertex.
     *
     * @param vertex the vertex to add to the graph
     */
    @Override
    public void addVertex(T vertex) {
        try {
            int vertexContains = super.getIndex(vertex);
        }//se não existir adiciona o vertice
        catch (ElementNotFoundException e) {
            super.addVertex(vertex);
            //Se estiver cheio , expande matriz de pesos
            if (this.getNumVertices() == this.getVertices().length) {
                this.expandCapacity();
            }
            for (int i = 0; i < this.getNumVertices(); i++) {
                this.weightAdjMatrix[this.getNumVertices() - 1][i] = new LinkedUnorderedList<>();
                this.weightAdjMatrix[i][this.getNumVertices() - 1] = new LinkedUnorderedList<>();
            }
        }
    }

    /**
     * Removes a vertex from the network
     *
     * @param vertex, the vertex to be removed
     */
    @Override
    public void removeVertex(T vertex) {
        try {
            this.removeVertex(this.getIndex(vertex));
            super.removeVertex(vertex);
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Removes a vertex from the network
     *
     * @param index, index of the vertex to be removed
     */
    private void removeVertex(int index) {

        for (int i = 0; i < this.getNumVertices(); i++) {
            for (int j = index; j < this.getNumVertices(); j++) {
                this.weightAdjMatrix[i][j] = this.weightAdjMatrix[i][j + 1];
            }
        }

        for (int i = index; i < this.getNumVertices(); i++) {
            System.arraycopy(this.weightAdjMatrix[i + 1], 0, this.weightAdjMatrix[i], 0, this.getNumVertices());
        }

    }

    /**
     * Inserts an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight the weight Extamente igual a implentação de uma network nao
     * direcionada
     */
    public void addEdge(T vertex1, T vertex2, Trajeto weight) {
        //so adiciona a edge caso ela nao exista
        if (!super.edgeExists(vertex1, vertex2)) {
            super.addEdge(vertex1, vertex2);
        }
        try {
            this.weightAdjMatrix[this.getIndex(vertex1)][this.getIndex(vertex2)].addToFront(weight);
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a directional edge between two vertices
     *
     * @param vertex1, the first vertex of the edge
     * @param vertex2, the second vertex of the edge
     */
    public void removeEdge(T vertex1, T vertex2, Trajeto target) {

        try {
            this.weightAdjMatrix[this.getIndex(vertex1)][this.getIndex(vertex2)].remove(target);

            if (this.weightAdjMatrix[this.getIndex(vertex1)][this.getIndex(vertex2)].isEmpty()) {
                super.removeEdge(vertex1, vertex2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public UnorderedListADT<Trajeto> shortestPathWeight(T vertex1, T vertex2) {
        return null;
    }

    @Override
    public String toString() {
        String result = "";

        /**
         * Print the adjacency Matrix
         */
        result += "Adjacency Matrix"
                + "\n----------------------------------\n"
                + "index\t";

        for (int i = 0; i < this.getNumVertices(); i++) {
            result += i;
            if (i < 10) {
                result += " ";
            }
        }
        result += "\n\n";

        for (int i = 0; i < this.getNumVertices(); i++) {
            result += i + "\t";

            for (int j = 0; j < this.getNumVertices(); j++) {
                if (!this.weightAdjMatrix[i][j].isEmpty()) {
                    result += "1 ";
                } else {
                    result += "0 ";
                }
            }
            result += "\n";
        }

        /**
         * Print the vertex values
         */
        result += "\n\nVertex Values"
                + "\n----------------------------------\n"
                + "index\tvalue\n\n";

        for (int i = 0; i < this.getNumVertices(); i++) {
            result += " " + i + "\t" + this.getVertices()[i].toString() + "\n";
        }

        /**
         * Print the weights of the edges
         */
        result += "\n\nWeights of Edges";

        for (int i = 0; i < this.getNumVertices(); i++) {
            for (int j = 0; j < this.getNumVertices() - 1; j++) {
                if (!this.weightAdjMatrix[i][j].isEmpty()) {
                    result += "\n----------------------------------\n"
                            + this.getVertices()[i].toString() + " para "
                            + this.getVertices()[j].toString() + ": "
                            + this.weightAdjMatrix[i][j].toString() + "\n";

                }
            }
        }

        return result;
    }

    @Override
    public T[] getVertices() {
        return super.getVertices(); //To change body of generated methods, choose Tools | Templates.
    }

}
