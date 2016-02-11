package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.Environment;
import cat.udl.eps.butterp.environment.NestedMap;
import cat.udl.eps.butterp.reader.LexerError;
import cat.udl.eps.butterp.reader.Parser;
import cat.udl.eps.butterp.reader.ParserError;
import cat.udl.eps.butterp.reader.StringLexer;
import spark.Request;
import spark.Response;
import spark.Session;

import com.google.gson.Gson;

import static spark.Spark.*;

public class WebRepl {

    private static class Repl {
        private final Environment environment = new NestedMap();

        public Repl() {
            Primitives.loadPrimitives(environment);
            Predefined.loadPredefined(environment);
        }

        public EvaluationDTO eval(String expression) {
            try {
                Parser parser = new Parser(new StringLexer(expression));
                SExpression sexpr = parser.sexpr();
                SExpression result = sexpr.eval(environment);
                return EvaluationDTO.ok(expression, result.toString());
            } catch (LexerError | ParserError | EvaluationError ex) {
                return EvaluationDTO.error(expression, ex.getMessage());
            }

        }
    }

    private static class EvaluationDTO {
        private String expression;
        private String result;
        private String error;

        private static EvaluationDTO ok(String expression, String result) {
            EvaluationDTO evaluationDTO = new EvaluationDTO();
            evaluationDTO.setExpression(expression);
            evaluationDTO.setResult(result);
            return evaluationDTO;
        }

        private static EvaluationDTO error(String expression, String error) {
            EvaluationDTO evaluationDTO = new EvaluationDTO();
            evaluationDTO.setExpression(expression);
            evaluationDTO.setError(error);
            return evaluationDTO;
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    private static Repl getOrCreateRepl(Session session) {
        Repl repl = session.attribute("repl");
        if (repl == null) {
            repl = new Repl();
            session.attribute("repl", repl);
        }
        return repl;
    }

    public static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }


    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        Gson gson = new Gson();
        staticFileLocation("/public");
        post("/eval", (req, res) -> {
            Repl repl = getOrCreateRepl(req.session(true));
            String expression = req.queryParams("expression");
            EvaluationDTO result = repl.eval(expression);
            return result;
        }, gson::toJson);
    }
}
