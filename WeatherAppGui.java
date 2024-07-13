import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;
    public WeatherAppGui(){
        super("Weather App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450,650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        addGuiComponentes();
    }

    private void addGuiComponentes(){

        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15,15,351,45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN,24));
        add(searchTextField);

        JLabel weatherConditionImage=new JLabel(LoadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0,125,450,217);
        add(weatherConditionImage);

        JLabel temperatureText=new JLabel("10 C");
        temperatureText.setBounds(0,350,450,54);
        temperatureText.setFont(new Font("Dialog",Font.BOLD,48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        JLabel weatherConditionDesc=new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0,405,450,36);
        weatherConditionDesc.setFont(new Font("Dialog",Font.PLAIN,32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        JLabel humidityImage=new JLabel(LoadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15,500,74,66);;
        add(humidityImage);

        JLabel humidityText=new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90,500,85,55);
        humidityText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(humidityText);

        JLabel windSpeedImage=new JLabel(LoadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(220,500,74,66);
        add(windSpeedImage);


        JLabel windSpeedText=new JLabel("<html><b>WindSpeed</b> 15Km/h</html>");
        windSpeedText.setBounds(310,500,85,55);
        windSpeedText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(windSpeedText);

        JButton searchButton = new JButton(LoadImage("src/assets/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,47,45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText();

                if (userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                // retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                if (weatherData == null) {
                    JOptionPane.showMessageDialog(null, "Unable to retrieve weather data.");
                    return;
                }

                // update image
                String weatherCondition = (String) weatherData.get("weather_condition");

                switch (weatherCondition) {
                    case "Cloudy":
                        weatherConditionImage.setIcon(LoadImage("src/assets/cloudy.png"));
                        break;
                    case "Clear":
                        weatherConditionImage.setIcon(LoadImage("src/assets/clear.png"));
                        break;
                    case "Rainy":
                        weatherConditionImage.setIcon(LoadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(LoadImage("src/assets/snow.png"));
                        break;
                }

                // update temp text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + "° C");

                // update weather cond text
                weatherConditionDesc.setText(weatherCondition);

                // update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>WindSpeed</b> " + windspeed + "Km/h</html>");
            }
        });
        add(searchButton);
    }

    private ImageIcon LoadImage(String resourcePath) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Could not find resource");
        return null;
    }
}
