package sorts;

import java.util.Arrays;

public class Mergesort<T extends Comparable<T>> implements ISorter<T> {
        private int comparisons = 0;
        private int swaps = 0;
        private Utils<T> utils = new Utils<T>();
        private long time = 0; // in ns
        
        private NaiveInPlace<T> naive = new NaiveInPlace();
        private OptimizedInPlace<T> optimized = new OptimizedInPlace();
        private OutOfPlace<T> outOfPlace = new OutOfPlace();

        Mergesort() {
            initializeCounters();
        }

        @Override
        public T[] sort(T[] dados) {
            startTime();
            naive.mergesort(dados, 0, dados.length - 1);
            endTime();
            System.out.println("IN-PLACE MERGESORT - VERSÃO SEM OTIMIZAÇÕES");
            System.out.println("Elapsed time: " + time + "ns");
            System.out.println("Array ordenado:");
            printArray(dados);

            startTime();
            optimized.mergesort(dados, 0, dados.length - 1);
            endTime();
            System.out.println("IN-PLACE MERGESORT - VERSÃO COM OTIMIZAÇÕES");
            System.out.println("Elapsed time: " + time + "ns");
            System.out.println("Array ordenado:");
            printArray(dados);

            startTime();
            outOfPlace.mergesort(dados);
            endTime();
            System.out.println("OUT-OF-PLACE MERGESORT");
            System.out.println("Elapsed time: " + time + "ns");
            System.out.println("Array ordenado:");
            printArray(dados);
            
            return dados;
        }

        // EXPERIMENTE: IMPLEMENTAR UMA VERSÃO QUE RETORNA UM NOVO ARRAY ORDENADO!
        private static class OutOfPlace<T extends Comparable<T>> {
            private T[] mergesort(T[] dados/*IMPLEMENTE!*/) {
                return null;
            }

            private T[] merge(/*IMPLEMENTE!*/) {
                return null;
            }
        }

        // MERGESORT AVANÇADO. NÃO VAI CAIR NA PROVA, MAS É INTERESSANTE ESTUDÁ-LO!
        private static class OptimizedInPlace<T extends Comparable<T>> {
            // Rotaciona o array entre inicio e fim, mantendo o elemento meio fixo
            // Exemplo: [1, 2, 3, 4, 5] -> [3, 4, 5, 1, 2]
            private void rotaciona(T[] arr, int inicio, int meio, int fim) {
                // Reverte a primeira metade
                reverte(arr, inicio, meio - 1);
                // Reverte a segunda metade  
                reverte(arr, meio, fim);
                // Reverte todo o intervalo
                reverte(arr, inicio, fim);
            }

            private void mergesort(T[] arr, int esq, int dir) {
                if (esq < dir) {
                    int meio = esq + (dir - esq) / 2;
                    
                    mergesort(arr, esq, meio);
                    mergesort(arr, meio + 1, dir);
                    
                    merge(arr, esq, meio, dir);
                }
            }
            
            private void merge(T[] arr, int esq, int meio, int dir) {
                int i = esq;
                int j = meio + 1;
                
                // Não precisa mesclar: O elemento do meio é menor que o elemento do direito
                if (arr[meio].compareTo(arr[j]) <= 0) {
                    return;
                }
    
                // Enquanto houver elementos em ambos os subarrays
                while (i <= meio && j <= dir) {
                    // Se o elemento do subarray esquerdo é menor que o do subarray direito,
                    // incrementa o apontador do subarray esquerdo
                    if (arr[i].compareTo(arr[j]) <= 0) {
                        i++;
                    } else {
                        // Em vez de deslocar, rotacione o subarray [i..j]
                        // Isso move arr[j] para a posição i e rotaciona o array para a esquerda
                        rotaciona(arr, i, j, j);
                        
                        // Atualiza os ponteiros.
                        // O meio é incrementado porque o elemento arr[j] foi movido para a posição i.
                        // O meio agora é + 1
                        i++;
                        meio++;
                        j++;
                    }
                }
            }

            // Inverte o array entre start e end. 
            // Exemplo: [1, 2, 3, 4, 5] -> [5, 4, 3, 2, 1]
            private void reverte(T[] arr, int inicio, int fim) {
                while (inicio < fim) {
                    // Troca entre inicio e fim
                    T temp = arr[inicio];
                    arr[inicio] = arr[fim];
                    arr[fim] = temp;
                    // Atualiza os ponteiros
                    inicio++;
                    fim--;
                }
            }
        }

        // MERGESORT IN-PLACE. IMPLEMENTAMOS EM SALA DE AULA!
        private static class NaiveInPlace<T extends Comparable<T>> {
            private void mergesort(T[] dados, int esq, int dir) {
                // Caso base: se o subarray tem apenas um elemento, não é necessário ordenar
                if (esq < dir) {
                    // Encontra o ponto médio do subarray
                    int meio = (dir - esq)/2 + esq;
                    // Recursivamente ordena as duas metades
                    mergesort(dados, esq, meio);
                    mergesort(dados, meio + 1, dir);
                    // Combina as duas metades ordenadas
                    merge(dados, esq, meio, dir);
                }
            }

            private void merge(T[] dados, int esq, int meio, int dir) {
                // Cria arrays auxiliares para armazenar as duas metades
                var arrEsq = Arrays.copyOfRange(dados, esq, meio+1);
                var arrDir = Arrays.copyOfRange(dados, meio+1, dir+1);
                // Apontador para o início do subarray esquerdo
                int i = 0;
                // Apontador para o início do subarray direito
                int j = 0;
                // Apontador para o início do subarray combinado
                int k = esq;
    
                // Enquanto houver elementos em ambos os subarrays
                while (i < arrEsq.length && j < arrDir.length) {
                    // Se o elemento do subarray direito é menor que o do esquerdo,
                    // coloca o elemento do subarray direito no array combinado
                    if (arrDir[j].compareTo(arrEsq[i]) < 0) {
                        dados[k] = arrDir[j];
                        j++;
                    } 
                    // Caso contrário, coloca o elemento do subarray esquerdo no array combinado
                    else {
                        dados[k] = arrEsq[i];
                        i++;
                    }
                    // Incrementa o apontador do array combinado
                    k++;
                }
    
                // Se pelo menos um dos subarrays foram inteiramente percorridos, então
                // o while acima é quebrado pela condição i < arrEsq.length ou j < arrDir.length.
                // Nesse caso, um dos subarrays podem ainda conter elementos não copiados
                // para o array combinado.

                // Copia os elementos restantes do subarray esquerdo, se houver
                while (i < arrEsq.length) {
                    dados[k] = arrEsq[i];
                    i++;
                    k++;
                }

                // Copia os elementos restantes do subarray direito, se houver
                while (j < arrDir.length) {
                    dados[k] = arrDir[j];
                    j++;
                    k++;
                }
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
        public long getTime() {
            // retornar o tempo decorrido
            return time;
        }

        private void initializeCounters() {
            comparisons = 0;
            swaps = 0;
            time = 0;
        }

        private void startTime() {
            // inicializa o temporizador
            time = System.nanoTime();
        }

        private void endTime() {
            // finaliza o temporizador
            time = System.nanoTime() - time;
        }

        private void printArray(T[] dados) {
            Arrays.stream(dados).forEach(
                item -> System.out.print(item + " ")
            );
            System.out.println();
        }
}