package de.lep.rmgc.model;

import de.lep.rmg.out.model.MidiInstrument;

/**
 * @author paul
 * @since 30.12.17.
 */
public class SongConfig {

	private MidiInstrument[] instruments = {
			MidiInstrument.getInstrument(MidiInstrument.PIANO),
			MidiInstrument.getInstrument(MidiInstrument.XYLOPHONE)
	};
	private int measureDivision = 8;
	private int chordNr = 4;
	private int chordDuration = 1;
	private int repeats = 1;


	public void setInstruments(MidiInstrument[] instruments) {
		this.instruments = instruments;
	}

	public void setMeasureDivision(int measureDivision) {
		this.measureDivision = measureDivision;
	}

	public void setChordNr(int chordNr) {
		this.chordNr = chordNr;
	}

	public void setChordDuration(int chordDuration) {
		this.chordDuration = chordDuration;
	}

	public void setRepeats(int repeats) {
		this.repeats = repeats;
	}

	public MidiInstrument[] getInstruments() {
		return instruments;
	}

	public int getMeasureDivision() {
		return measureDivision;
	}

	public int getChordNr() {
		return chordNr;
	}

	public int getChordDuration() {
		return chordDuration;
	}

	public int getRepeats() {
		return repeats;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("-- Configuration --\n\n");
		out.append("\tNumber of Chords per Melody:\t");
		out.append(chordNr);
		out.append("\n");
		out.append("\tDuration of Chords:\t");
		out.append(chordDuration);
		out.append("\n");
		out.append("\tNumber of Repeats:\t");
		out.append(repeats);
		out.append("\n");
		out.append("\tMeasure Division:\t");
		out.append(measureDivision);
		out.append("\n");
		out.append("\tInstruments:\n");
		for (MidiInstrument inst : instruments) {
			out.append("\t\t");
			out.append(inst.toString());
			out.append("\n");
		}
		return out.toString();
	}
}
