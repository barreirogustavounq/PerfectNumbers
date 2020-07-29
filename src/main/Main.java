import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
        static long time = 0;
        public static void main(String args[]) {

                /** Genero el input del buffer*/
                Scanner inputBuffer = new Scanner(System.in);
                String bufferSize;
                System.out.println("Ingrasar el tamaño del buffer");
                bufferSize = inputBuffer.nextLine();

                /** Inicializo un buffer */
                Buffer buffer = new Buffer(Integer.parseInt(bufferSize));

                /** Genero el input de la cantidad de threads que quiero utilizar */
                Scanner inputThreads = new Scanner(System.in);
                String cantOfThreads;
                System.out.println("Ingrasar la cantidad de Threads");
                cantOfThreads = inputThreads.nextLine();


                /** Genero el imput de la cantidad de numeros del 1 al N que se van a generar y poner en el buffer*/
                Scanner inputNumbers = new Scanner(System.in);
                String numbersToExecute;
                System.out.println("Ingrasar la cantidad de numeros a analizar (los mismos seran desde 1 hasta la cantidad Ingresada:)");
                numbersToExecute = inputNumbers.nextLine();

                /** Inincializo una Barrier*/
                int threadsForBarrier = Integer.parseInt(cantOfThreads);

                Barrier barrier = new Barrier(threadsForBarrier);

                /** Genero el imput de la cantidad de numeros del 1 al N que se van a generar y poner en el buffer*/
                Scanner inputPerfects = new Scanner(System.in);
                String perfectsNumbers;
                System.out.println("Ingrasar la cantidad de numeros a perfectos a analizar");
                perfectsNumbers = inputPerfects.nextLine();

                /** Genero la lista de numeros perfectos*/
                PerfectsNumbersCalculator perfectsNumbersCalculator = new PerfectsNumbersCalculator();

                /** Tomo el tiempo desde que comienzan a trabajar los Threads*/
                long inicio = System.currentTimeMillis();
                time = inicio;
                perfectsNumbersCalculator.calculatePerfects(Integer.parseInt(perfectsNumbers));

                ejecutar(barrier, buffer, Integer.parseInt(numbersToExecute), perfectsNumbersCalculator.getListOfPerfects());
                int activeThreads = Integer.parseInt(numbersToExecute);
                if(activeThreads > barrier.getMaxThreads()){
                        activeThreads = barrier.getMaxThreads();
                }

                while (barrier.getFinished() < activeThreads ){
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("espero que termine");
                }

                long fin = System.currentTimeMillis();
                getTime(fin - time, Integer.parseInt(cantOfThreads));
                getTotal(barrier);
        }

        public static void ejecutar(Barrier barrier, Buffer buffer, int numbersToExecute, List<BigInteger> listOfPerfects ){

                /** Inicializo un ThreadPool*/
                ThreadPool threadPool = new ThreadPool(barrier.getMaxThreads());


                /** ejecuto el metodo startWorkers del threadPool*/
                threadPool.startWorkers(buffer, barrier, numbersToExecute, listOfPerfects);
                /** Inserto los diferentes numeros al buffer*/
                BigInteger cantOfNumber = new BigInteger("0");
                while (cantOfNumber.compareTo(BigInteger.valueOf(numbersToExecute)) < 0 ) {
                        buffer.push(cantOfNumber);
                        cantOfNumber = cantOfNumber.add(BigInteger.valueOf(1));
                        System.out.print("agregue " + cantOfNumber +"\n");
                }
                System.out.println("sali del while con " + cantOfNumber);
                //buffer.push(new BigInteger("33550336"));
                //buffer.push(new BigInteger("8589869056"));
                //buffer.push(new BigInteger("137438691328"));
                //buffer.push(new BigInteger("2305843008139952128"));
                //buffer.push(new BigInteger("2658455991569831744654692615953842176"));
                //buffer.push(new BigInteger("191561942608236107294793378084303638130997321548169216"));

                /** Inserto los numeros negativos para que el thread termine de ejecutar una vez que tome ese numero*/
                int cantOfNegativeNumbers = 0;
                while (cantOfNegativeNumbers < barrier.getMaxThreads()) {
                        buffer.push((BigInteger.valueOf(1)).negate());
                        cantOfNegativeNumbers++;
                        System.out.print("agregue  " + cantOfNegativeNumbers + " -1 \n");
                }
        }

        private static void getTime(long timeOfExecution ,int cantOfThreads){
                System.out.println("El tiempo de ejecucion con " + cantOfThreads + " Threads son : \n" + timeOfExecution + " ms.");
        }

        private static void getTotal(Barrier barrier){
                System.out.println("Los numeros Perfectos son:");
                System.out.println(barrier.getResulTotal());
        }
}