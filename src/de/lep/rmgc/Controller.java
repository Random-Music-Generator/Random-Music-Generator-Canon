package de.lep.rmgc;

import de.lep.rmg.out.model.MidiInstrument;
import de.lep.rmg.out.model.MidiSong;
import de.lep.rmg.view.IOInterface;
import de.lep.rmg.view.ViewController;
import de.lep.rmg.view.ViewListener;
import de.lep.rmgc.model.SongConfig;

import java.util.Map;
import java.util.Random;

/**
 * @author paul
 * @since 30.12.17.
 */
public class Controller implements ViewListener {

	private static SongConfig config;


	public static void main(String[] args) {
		config = new SongConfig();
		ViewController.setViewListener(new Controller());
		ViewController.init();
	}

	/**
	 * A new Song should be generated.
	 *
	 * @return The generated Song
	 */
	@Override
	public MidiSong generateSong() {
		// TODO generate Song ;)
		return null;
	}

	/**
	 * The Song-Config should be printed
	 */
	@Override
	public void printConfig() {
		if (config == null) {
			IOInterface.printError("Configuration not set");
		} else {
			IOInterface.printMessage(config.toString());
		}
	}

	/**
	 * A Random Song-Config should be created
	 *
	 * @return Whether a new Config got created
	 */
	@Override
	public boolean setRandomConfig() {
		Random r = new Random();
		int chordNr = r.nextInt(3)+1;
		int chordDuration = r.nextInt(3)+1;
		int repeats = r.nextInt(3)+1;
		int measureDivision = 8;
		// TODO Check Chord-Duration and Chord-Nr
		MidiInstrument[] instruments = new MidiInstrument[r.nextInt(4)+1];
		for (int i = 0; i < instruments.length; i++) {
			MidiInstrument[] available = MidiInstrument.getInstruments();
			MidiInstrument inst = available[r.nextInt(available.length-1)];
			instruments[i] = new MidiInstrument(inst.getName(), inst.getShortName(), inst.getMidiProgram());
		}

		config = new SongConfig();
		config.setChordNr(chordNr);
		config.setRepeats(repeats);
		config.setMeasureDivision(measureDivision);
		config.setChordDuration(chordDuration);
		config.setInstruments(instruments);
		IOInterface.printMessage("Generated Configuration:\n" + config.toString());
		return true;
	}

	/**
	 * A Dialog which allows the User to set a new Config should be printed
	 *
	 * @return Whether a new Config go created
	 */
	@Override
	public boolean printSetConfigDialog() {
		// Getting the old Configuration
		int chordNr = config.getChordNr();
		int chordDuration = config.getChordDuration();
		int repeats = config.getRepeats();
		int measureDivision = config.getMeasureDivision();
		MidiInstrument[] instruments = config.getInstruments();

		// Get Input for new Configuration
		IOInterface.printMessage("-- New Configuration --");
		IOInterface.printMessage("Leave argument empty to get default value");
		chordNr = IOInterface.getInputRange(0, 4, "Number of Chords per Melody", chordNr);
		chordDuration = IOInterface.getInputRange(0, 4, "Duration of a Chord", chordDuration);
		// TODO Check Chord-Duration and Chord-Nr
		repeats = IOInterface.getInputRange(0, 4, "Number of Repeats", repeats);
		String[] mdOptions = {Integer.toString(measureDivision), "4", "8", "16", "32"};
		int mdSelected = IOInterface.getInputSelect(
				mdOptions,
				"Measure-Division", 0);
		measureDivision = Integer.parseInt(mdOptions[mdSelected]);

		// Get Instruments
		int numberOfInstruments = IOInterface.getInputInt("Number of Instruments", instruments.length);
		MidiInstrument[] newInstruments = new MidiInstrument[numberOfInstruments];
		for (int i = 0; i < newInstruments.length; i++) {
			MidiInstrument[] available = MidiInstrument.getInstruments();
			String[] options;

			// Get previous, custom or preset Instrument
			if (i < instruments.length) {
				options = new String[available.length+2];
				options[options.length-2] = String.format("Previous Instrument: %s (Program: %d)",
						instruments[i].getName(), instruments[i].getMidiProgram());
			} else {
				options = new String[available.length+1];
			}

			for (int j = 0; j < available.length; j++) {
				options[j] = String.format("%s (Program: %d)",
						available[j].getName(), available[j].getMidiProgram());
			}

			options[options.length-1] = "Custom Instrument";

			int choosen = IOInterface.getInputSelect(options, (i+1) + ". Instrument");

			String name;
			String shortName;
			int midiProgram;
			int volume = 100;
			if (choosen == options.length-1) {  // Custom Instrument
				name = IOInterface.getInputString("Name of Instrument", "Unnamed");
				shortName = IOInterface.getInputString("Short-Name of Instrument",
						name.substring(0, 4));
				midiProgram = IOInterface.getInputInt("Midi-Program of Instrument");
			} else if (options.length == available.length + 2) {
				name = instruments[i].getName();
				shortName = instruments[i].getShortName();
				midiProgram = instruments[i].getMidiProgram();
				volume = (int) instruments[i].getVolume();
			} else {
				name = available[choosen].getName();
				shortName = available[choosen].getShortName();
				midiProgram = available[choosen].getMidiProgram();
			}

			volume = IOInterface.getInputInt("Volume of the Instrument", volume);
			MidiInstrument newInstrument = new MidiInstrument(name, shortName, midiProgram, volume);
			newInstruments[i] = newInstrument;
		}

		// Set new Configuration
		config = new SongConfig();
		config.setChordDuration(chordDuration);
		config.setChordNr(chordNr);
		config.setInstruments(newInstruments);
		config.setMeasureDivision(measureDivision);
		config.setRepeats(repeats);

		// Show Configuration
		IOInterface.printMessage("\n New Configuration:\n" + config.toString());
		return true;
	}

	/**
	 * The Interface will be closed
	 */
	@Override
	public void close() {

	}
}
