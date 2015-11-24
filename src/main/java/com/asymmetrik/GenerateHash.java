package com.asymmetrik;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.*;

import com.asymmetrik.utils.hash.*;

/**
 * App to run average hash generator for the given image
 */
public class GenerateHash {

	private static final String AVERAGE_HASH_TYPE = "average";
	
	private static void printUsage(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("GenerateHash <imagePath>", options, true);
	}
	
    public static void main(String[] args) throws IOException {
		try {
			// Parse the command line argument
			CommandLineParser parser = new DefaultParser();
			
			Options options = new Options();
			options.addOption("h", "help", false, "Prints usage.");
			options.addOption(OptionBuilder.withArgName("hashType")
							  .isRequired(false)
							  .hasArg()
							  .withDescription("Hash algorithm to use. Currently only supports \"average\"")
							  .create("t"));
			
			CommandLine cmd = parser.parse(options, args);
			
			if (cmd.hasOption("h")) {
				printUsage(options);
			} else {
				// Parse options
				String hashType = cmd.getOptionValue("t", AVERAGE_HASH_TYPE);
				
				List<String> remainingArgs = cmd.getArgList();
				if (remainingArgs.size() == 1) {
					IHashGen hashGen;
					if (hashType.equals(AVERAGE_HASH_TYPE)) {
						hashGen = new AverageHash();
						System.out.println(hashGen.getHash(new FileInputStream(remainingArgs.get(0))));
					} else {
						System.out.println(hashType + " hash type not supported.");
					}
				} else {
					printUsage(options);
				}
			}
		} catch (Exception e) {
			System.err.println("Error running GenerateHash." + e.getMessage());
		}
    }

}
