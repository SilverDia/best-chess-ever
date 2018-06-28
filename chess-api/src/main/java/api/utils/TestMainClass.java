package api.utils;

import api.chess.equipment.board.Coordinates;
import api.chess.equipment.board.Square;
import api.chess.equipment.pieces.Pawn;
import api.chess.equipment.pieces.Piece;
import api.chess.gameplay.game.Game;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class TestMainClass {

    public static void main(String[] args){
        // TODO add all little tests here...
        Game game = new Game();
        game.init("w", "b");
        System.out.println(new Gson().toJson(game));

        try {

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(File.createTempFile("initialGame", ".json")));
            bufferedWriter.write(new Gson().toJson(game));
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
