import java.util.*;
class QuizQuestion {
    String question;
    String[] options;
    int correctAnswerIndex;

    public QuizQuestion(String question, String[] options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

public class QuizApp {
    private static final int QUESTION_TIME_LIMIT = 10; // Time limit in seconds per question
    private static final QuizQuestion[] questions = {
        new QuizQuestion("What is the capital of France?", new String[]{"1. Berlin", "2. London", "3. Paris", "4. Madrid"}, 3),
        new QuizQuestion("Who wrote 'To Kill a Mockingbird'?", new String[]{"1. Harper Lee", "2. Mark Twain", "3. J.K. Rowling", "4. Ernest Hemingway"}, 1),
        new QuizQuestion("What is the chemical symbol for water?", new String[]{"1. O2", "2. H2O", "3. CO2", "4. H2"}, 2)
    };
    private static int score = 0;
    private static boolean answerSubmitted = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < questions.length; i++) {
            displayQuestion(i, scanner);
        }

        System.out.println("Quiz Over! Your final score is: " + score + "/" + questions.length);
        scanner.close();
    }

    private static void displayQuestion(int questionIndex, Scanner scanner) {
        QuizQuestion question = questions[questionIndex];
        System.out.println("\nQuestion " + (questionIndex + 1) + ": " + question.question);
        for (String option : question.options) {
            System.out.println(option);
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!answerSubmitted) {
                    System.out.println("\nTime's up! Moving to the next question.");
                    answerSubmitted = true;
                }
            }
        };

        timer.schedule(task, QUESTION_TIME_LIMIT * 1000);
        System.out.print("Enter your answer (1-4): ");
        int answer = -1;

        while (!answerSubmitted) {
            if (scanner.hasNextInt()) {
                answer = scanner.nextInt();
                if (answer >= 1 && answer <= 4) {
                    answerSubmitted = true;
                    timer.cancel();
                    if (answer == question.correctAnswerIndex) {
                        score++;
                        System.out.println("Correct!");
                    } else {
                        System.out.println("Incorrect. The correct answer was " + question.correctAnswerIndex + ".");
                    }
                } else {
                    System.out.println("Please enter a valid option (1-4).");
                }
            } else {
                scanner.next(); // Clear invalid input
            }
        }

        answerSubmitted = false;
    }
}
