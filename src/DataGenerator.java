import java.io.*;
import java.util.Random;

public class DataGenerator {

    public static void main(String[] args) {

        Random r = new Random();

        BufferedWriter configWriter = null;
        String hairLength = "";
        String[] hairLengthAtt = new String[5];

        String glasses = "";
        String[] glassesAtt = new String[5];

        String facialHair = "";
        String[] facialHairAtt = new String[5];

        String eyeColor = "";
        String[] eyeColorAtt = new String[6];

        String pimples = "";
        String[] pimplesAtt = new String[4];

        String hat = "";
        String[] hatAtt = new String[3];

        String hairColor = "";
        String[] hairColorAtt = new String[8];

        String noseShape = "";
        String[] noseShapeAtt = new String[6];

        String faceShape = "";
        String[] faceShapeAtt = new String[4];

        try {
            // Generate 10 config files
            for (int i = 1; i <= 5; i++) {
                configWriter = new BufferedWriter(new FileWriter("game" + i + ".config"));

                hairLength = "hairLength none short medium long\n";
                configWriter.write(hairLength);

                for (int j = 0; j < hairLengthAtt.length; j++) {
                    hairLengthAtt = hairLength.split("\\s");
                }

                glasses = "glasses none circle rectangle triangle\n";
                configWriter.write(glasses);
                for (int j = 0; j < glassesAtt.length; j++) {
                    glassesAtt = glasses.split("\\s");
                }

                facialHair = "facialHair black brown grey yellow\n";
                configWriter.write(facialHair);
                for (int j = 0; j < facialHairAtt.length; j++) {
                    facialHairAtt = facialHair.split("\\s");
                }

                eyeColor = "eyeColor black brown purple blue green\n";
                configWriter.write(eyeColor);
                for (int j = 0; j < eyeColorAtt.length; j++) {
                    eyeColorAtt = eyeColor.split("\\s");
                }

                pimples = "pimples none some many\n";
                configWriter.write(pimples);
                for (int j = 0; j < pimplesAtt.length; j++) {
                    pimplesAtt = pimples.split("\\s");
                }

                hat = "hat on off\n";
                configWriter.write(hat);
                for (int j = 0; j < hatAtt.length; j++) {
                    hatAtt = hat.split("\\s");
                }

                hairColor = "hairColor black grey blue purple red green grey\n";
                configWriter.write(hairColor);
                for (int j = 0; j < hairColorAtt.length; j++) {
                    hairColorAtt = hairColor.split("\\s");
                }

                noseShape = "noseShape fleshy turnedUp hawk roman greek\n";
                configWriter.write(noseShape);
                for (int j = 0; j < noseShapeAtt.length; j++) {
                    noseShapeAtt = noseShape.split("\\s");
                }

                faceShape = "faceShape circle rectangle triangle\n\n";
                configWriter.write(faceShape);
                for (int j = 0; j < faceShapeAtt.length; j++) {
                    faceShapeAtt = faceShape.split("\\s");
                }

                // Generate 20 personas
                for (int k = 0; k < 20; k++) {
                    configWriter.write("P" + k + "\n");

                    configWriter.write("hairLength " + hairLengthAtt[r.nextInt(hairLengthAtt.length - 1) + 1] + "\n");
                    configWriter.write("glasses " + glassesAtt[r.nextInt(glassesAtt.length - 1) + 1] + "\n");
                    configWriter.write("facialHair " + facialHairAtt[r.nextInt(facialHairAtt.length - 1) + 1] + "\n");
                    configWriter.write("eyeColor " + eyeColorAtt[r.nextInt(eyeColorAtt.length - 1) + 1] + "\n");
                    configWriter.write("pimples " + pimplesAtt[r.nextInt(pimplesAtt.length - 1) + 1] + "\n");
                    configWriter.write("hat " + hatAtt[r.nextInt(hatAtt.length - 1) + 1] + "\n");
                    configWriter.write("hairColor " + hairColorAtt[r.nextInt(hairColorAtt.length - 1) + 1] + "\n");
                    configWriter.write("noseShape " + noseShapeAtt[r.nextInt(noseShapeAtt.length - 1) + 1] + "\n");
                    configWriter.write("faceShape " + faceShapeAtt[r.nextInt(faceShapeAtt.length - 1) + 1] + "\n\n");
                }
                configWriter.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            System.err.println(ioe.getLocalizedMessage());
        } finally {
            try {
                if (configWriter != null) {
                    configWriter.close();
                }
            } catch (IOException ioe) {
                System.err.println(ioe.getLocalizedMessage());
            }
        }
    }
}
