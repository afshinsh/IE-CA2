import io.javalin.Javalin;

public class Startup {



    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);
        app.get("/", ctx -> ctx.result("Welcome To IEMDB!"));
    }
}
