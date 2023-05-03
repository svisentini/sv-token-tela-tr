import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
        static String getEnvironment(String environment) {
                switch(environment) {
                        case "Dev":
                                environment = "dev";
                                break;
                        case "QA":
                                environment = "qa";
                                break;

                        default:
                                environment = "dev";
                }
                return environment;
        }

        static String getCodSistema(String sistemaAplicacao) {
                switch(sistemaAplicacao) {
                        case "Export":
                                sistemaAplicacao = "2";
                                break;
                        case "Import":
                                sistemaAplicacao = "9";
                                break;
                        case "InOut":
                                sistemaAplicacao = "1000";
                        case "MasterData":
                                sistemaAplicacao = "0";
                                break;
                        default:
                                sistemaAplicacao = "2";
                }
                return sistemaAplicacao;
        }


}


