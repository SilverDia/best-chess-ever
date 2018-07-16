package api.config;

import api.chess.equipment.pieces.Piece;
import api.chess.equipment.pieces.PieceSet;
import api.chess.gameplay.game.Game;

import java.util.logging.Logger;

import static api.config.GameConfig.RESOURCE_PATH_IMAGES_PIECESETS;

public class PieceConfig {
	private final static Logger LOG = Logger.getLogger(PieceConfig.class.getName());

	public enum PieceName {
		PAWN {

			@Override
			public String getDE() {
				return "Bauer";
			}
		},
		ROOK {

			@Override
			public String getDE() {
				return "Turm";
			}
		},
		KNIGHT {

			@Override
			public String getDE() {
				return "Pferd";
			}
		},
		BISHOP {

			@Override
			public String getDE() {
				return "L&auml;ufer";
			}
		},
		QUEEN {

			@Override
			public String getDE() {
				return "Dame";
			}
		},
		KING {

			@Override
			public String getDE() {
				return "K&ouml;nig";
			}
		};
		public abstract String getDE();
	}

	public enum Color {
		BLACK {

			@Override
			public String getDE() {
				return "Schwarz";
			}
		},
		WHITE {

			@Override
			public String getDE() {
				return "Weiss";
			}
		};
		public abstract String getDE();
	}
}
