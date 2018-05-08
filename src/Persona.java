
public class Persona {

    public String name, hairLength, glasses, facialHair, eyeColor, pimples, hat, hairColor, noseShape,
            faceShape;

    public Persona(String name, String hairLength, String glasses, String facialHair, String eyeColor, String pimples,
            String hat, String hairColor, String noseShape, String faceShape) {
        this.name = name;
        this.hairLength = hairLength;
        this.glasses = glasses;
        this.facialHair = facialHair;
        this.eyeColor = eyeColor;
        this.pimples = pimples;
        this.hat = hat;
        this.hairColor = hairColor;
        this.noseShape = noseShape;
        this.faceShape = faceShape;
    }

    public String identifyPersona(String att) {
        switch (att) {
        case "name":
            return name;
        case "hairLength":
            return hairLength;
        case "glasses":
            return glasses;
        case "facialHair":
            return facialHair;
        case "eyeColor":
            return eyeColor;
        case "pimples":
            return pimples;
        case "hat":
            return hat;
        case "hairColor":
            return hairColor;
        case "noseShape":
            return noseShape;
        case "faceShape":
            return faceShape;
        default:
            return "unknown";
        }

    }
}
