
package sorts;

import java.util.Arrays;

public class Mergesort<T extends Comparable<T>> implements ISorter<T> {
        private int comparisons = 0;
        private int swaps = 0;
        private Utils<T> utils = new Utils<T>();
        private int time = 0; // in ms
    
        @Override
        public T[] sort(T[] dados) {
            mergesort(dados, 0, dados.length - 1);

            return dados;
        }
    
        private void mergesort(T[] dados, int esq, int dir) {
            if (esq < dir) {
                int meio = (dir - esq)/2 + esq;
                mergesort(dados, esq, meio);
                mergesort(dados, meio + 1, dir);

                merge(dados, esq, meio, dir);
            }
        }

        private void merge(T[] dados, int esq, int meio, int dir) {
            var arrEsq = Arrays.copyOfRange(dados, esq, meio+1);
            var arrDir = Arrays.copyOfRange(dados, meio+1, dir+1);

            int i = 0;
            int j = 0;

            int k = esq;

            while (i < arrEsq.length && j < arrDir.length) {
                if (arrDir[j].compareTo(arrEsq[i]) < 0) {
                    dados[k] = arrDir[j];
                    j++;
                } else {
                    dados[k] = arrEsq[i];
                    i++;
                }
                k++;
            }

            while (i < arrEsq.length) {
                dados[k] = arrEsq[i];
                i++;
                k++;
            }

            while (j < arrDir.length) {
                dados[k] = arrDir[j];
                j++;
                k++;
            }
        }

        @Override
        public int getComparisons() {
            return comparisons;
        }
    
        @Override
        public int getSwaps() {
            return swaps;
        }
    
        @Override
        public int getTime() {
            // retornar o tempo decorrido
            return time;
        }

        private void initializeCounters() {
            comparisons = 0;
            swaps = 0;
        }

        private void startTime() {
            // inicializa o temporizador
        }

        private void endTime() {
            // finaliza o temporizador
        }
}