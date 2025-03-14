package sorts;

import java.util.Arrays;

public class Bubblesort<T extends Comparable<T>> implements ISorter<T> {
        private int comparisons = 0;
        private int swaps = 0;
        private Utils<T> utils = new Utils<T>();
        private int time = 0; // in ms
    
        @Override
        public T[] sort(T[] dados) {
            T[] arrayCopy = Arrays.copyOf(dados, dados.length);

            // It sorts:
            int n = arrayCopy.length;

            initializeCounters();
            startTime();
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < n - i; j++) {
                    comparisons++;
                    if (arrayCopy[j].compareTo(arrayCopy[j+1]) > 0) {
                        utils.swap(arrayCopy, j, j+1);
                    }
                }
            }
            endTime();

            return arrayCopy;
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