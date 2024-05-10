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

public class Actions {
        static void actionExit(JFrame frame) {
                frame.dispose();
        }

        static void actionCopyClipboard(JTextArea tokenArea) {
//                tokenArea.setText(environmentComboBox.getSelectedItem().toString());

                StringSelection selection = new StringSelection(tokenArea.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
        }
        static String actionGenerate(JTextField nameInput, JTextField passwordInput, JComboBox<String> systemComboBox, JComboBox<String> environmentComboBox, JTextArea tokenArea) throws IOException {
                tokenArea.setText("Processing .....");
                String username = nameInput.getText();
                String password = passwordInput.getText();
                String systemCode = Utils.getCodSistema(systemComboBox.getSelectedItem().toString().trim());
                String companyId = Utils.getEnvironment(environmentComboBox.getSelectedItem().toString().trim());


                if (username == null){
                        username = "0180500";
                }
                if (password == null){
                        password = "swadm123";
                }


                String url = "https://ogt-gtm-next-dev.int.thomsonreuters.com/auth-v2-service/oauth2/token";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // Configurar a requisição
                con.setRequestMethod("POST");
                con.setRequestProperty("companyId", companyId);
                con.setRequestProperty("Accept-Language", "pt-BR");
                con.setRequestProperty("Authorization", "Basic Y2xpZW50OjEyMw==");
                con.setRequestProperty("Cookie", "SESSION=ODA2Y2QwZTMtNTIzOS00Mjk2LTkzM2ItZjhmMTRmODMxOTEy; SESSION=YmU4ODc1ZTQtMzg3Yy00N2U1LWFjODYtMTQ5NGM4M2UzYWE4");

                // Definir o corpo da requisição
                String urlParameters = "grant_type=urn:ietf:params:oauth:grant-type:password&username=" + username + "&password=" + password;
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(urlParameters.getBytes());
                os.flush();
                os.close();

                // Obter a resposta
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                }
                in.close();


                String responseBody = response.toString();
                String accessToken = responseBody.substring(responseBody.indexOf(":\"") + 2, responseBody.indexOf("suggests_password_change")-3);



                // =========================================================================================


                url = "https://ogt-gtm-next-dev.int.thomsonreuters.com/auth-v2-service/oauth2/token?systemCode=" + systemCode;
                obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();

                // Configurar a requisição
                con.setRequestMethod("POST");
                con.setRequestProperty("companyId", companyId);
                con.setRequestProperty("Authorization", "Basic Y2xpZW50OjEyMw==");
                con.setRequestProperty("Cookie", "SESSION=ODA2Y2QwZTMtNTIzOS00Mjk2LTkzM2ItZjhmMTRmODMxOTEy; SESSION=YmU4ODc1ZTQtMzg3Yy00N2U1LWFjODYtMTQ5NGM4M2UzYWE4");

                // Definir o corpo da requisição
                urlParameters = "grant_type=urn:ietf:params:oauth:grant-type:access_token&access_token=" + accessToken;
                con.setDoOutput(true);
                os = con.getOutputStream();
                os.write(urlParameters.getBytes());
                os.flush();
                os.close();

                // Obter a resposta
                responseCode = con.getResponseCode();
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                }
                in.close();

                return response.toString().substring(response.toString().indexOf(":\"") + 2, response.toString().indexOf("token_type")-3);

        }
}
