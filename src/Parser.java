import GameParser.BattleShipGame;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.spi.FileTypeDetector;

public class Parser {

    private BattleShipGame ParsedGame;

    public BattleShipGame GetParsedGame() {
        return ParsedGame;
    }

    Parser(String xmlPath) throws Exception {
        ParseXML(xmlPath);
    }



    private void ParseXML(String xmlPath) throws Exception {
        try {
            if(xmlPath == null) {
                throw new Exception("Please add path to xml file.");
            }
            File file = new File(xmlPath);

            if(file.exists() && !file.isDirectory()) {

                int dotIndex = file.getName().lastIndexOf('.');
                if(dotIndex == -1) {
                    throw new Exception("File extension is not recognize.");
                } else {
                    if(!file.getName().substring(dotIndex + 1).equals("xml")) {
                        throw new Exception("File must be of format fxml .");
                    }
                }
                JAXBContext jaxbContext = JAXBContext.newInstance(BattleShipGame.class);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                ParsedGame = (BattleShipGame) jaxbUnmarshaller.unmarshal(file);
            } else {
                throw new Exception("File " + xmlPath + " is not exsist ." );
            }


        } catch (JAXBException e) {
            throw new Exception("File Cannot be Parsed by JAXB");
        }
    }

    public String getFileExtension(String fullName) throws Exception {
        if(fullName == null); {
            throw new Exception("Path is null");
        }
    }
}
