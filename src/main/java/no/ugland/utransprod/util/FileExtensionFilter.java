package no.ugland.utransprod.util;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.swing.filechooser.FileFilter;

public class FileExtensionFilter extends FileFilter {

	private Map<String, FileFilter> filters = null;

	private String description = null;

	private String fullDescription = null;

	private boolean useExtensionsInDescription = true;

	/**
	 * Creates a file filter. If no filters are added, then all files are
	 * accepted.
	 * 
	 * @see #addExtension
	 */
	public FileExtensionFilter() {
		this.filters = new Hashtable<String, FileFilter>();
	}

	/**
	 * Creates a file filter that accepts files with the given extension.
	 * Example: new ExampleFileFilter("jpg");
	 * 
	 * @param aExtension
	 * @see #addExtension
	 */
	public FileExtensionFilter(final String aExtension) {
		this(aExtension, null);
	}

	/**
	 * Creates a file filter that accepts the given file type. Example: new
	 * ExampleFileFilter("jpg", "JPEG Image Images"); Note that the "." before
	 * the extension is not needed. If provided, it will be ignored.
	 * 
	 * @param aExtension
	 * @param aDescription
	 * @see #addExtension
	 */
	public FileExtensionFilter(final String aExtension,
			final String aDescription) {
		this();
		if (aExtension != null) {
			addExtension(aExtension);
		}
		if (aDescription != null) {
			setDescription(aDescription);
		}
	}

	/**
	 * Creates a file filter from the given string array. Example: new
	 * ExampleFileFilter(String {"gif", "jpg"}); Note that the "." before the
	 * extension is not needed adn will be ignored.
	 * 
	 * @param someFilters
	 * @see #addExtension
	 */
	public FileExtensionFilter(final String[] someFilters) {
		this(someFilters, null);
	}

	/**
	 * Creates a file filter from the given string array and description.
	 * Example: new ExampleFileFilter(String {"gif", "jpg"}, "Gif and JPG
	 * Images"); Note that the "." before the extension is not needed and will
	 * be ignored.
	 * 
	 * @param someFilters
	 * @param aDescription
	 * @see #addExtension
	 */
	public FileExtensionFilter(final String[] someFilters,
			final String aDescription) {
		this();
		for (int i = 0; i < someFilters.length; i++) {
			// add filters one by one
			addExtension(someFilters[i]);
		}
		if (aDescription != null) {
			setDescription(aDescription);
		}
	}

	/**
	 * Return true if this file should be shown in the directory pane, false if
	 * it shouldn't. Files that begin with "." are ignored.
	 * 
	 * @param f
	 * @return true
	 * @see #getExtension
	 */
	@Override
	public final boolean accept(final File f) {
		if (f != null) {
			if (f.isDirectory()) {
				return true;
			}
			String extension = getExtension(f);
			if (extension != null && filters.get(getExtension(f)) != null) {
				return true;
			}

		}
		return false;
	}

	/**
	 * Return the extension portion of the file's name .
	 * 
	 * @param f
	 * @return extension
	 * @see #getExtension
	 * @see FileFilter#accept
	 */
	public static final String getExtension(final File f) {
		if (f != null) {
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(i + 1).toLowerCase();
			}

		}
		return null;
	}

	/**
	 * Adds a filetype "dot" extension to filter against. For example: the
	 * following code will create a filter that filters out all files except
	 * those that end in ".jpg" and ".tif": ExampleFileFilter filter = new
	 * ExampleFileFilter(); filter.addExtension("jpg");
	 * filter.addExtension("tif"); Note that the "." before the extension is not
	 * needed and will be ignored.
	 * 
	 * @param aExtension
	 */
	public final void addExtension(final String aExtension) {
		if (filters == null) {
			filters = new Hashtable<String, FileFilter>(5);
		}
		filters.put(aExtension.toLowerCase(), this);
		fullDescription = null;
	}

	/**
	 * Returns the human readable description of this filter. For example: "JPEG
	 * and GIF Image Files (*.jpg, *.gif)"
	 * 
	 * @return description
	 * @see FileFilter#getDescription
	 */
	@Override
	public final String getDescription() {
		if (fullDescription == null) {
			if (description == null || isExtensionListInDescription()) {
				if (description == null) {
					fullDescription = "(";
				} else {
					fullDescription = description + " (";
				}
				// build the description from the extension list
				Set<String> extensions = filters.keySet();
				if (extensions != null) {
					boolean firstElement = true;
					for (String ex : extensions) {
						if (firstElement) {
							fullDescription += "." + ex;
							firstElement = false;
						}
						fullDescription += ", ." + ex;
					}

				}
				fullDescription += ")";
			} else {
				fullDescription = description;
			}
		}
		return fullDescription;
	}

	/**
	 * Sets the human readable description of this filter. For example:
	 * filter.setDescription("Gif and JPG Images");
	 * 
	 * @param aDescription
	 */
	public final void setDescription(final String aDescription) {
		this.description = aDescription;
		fullDescription = null;
	}

	/**
	 * Determines whether the extension list (.jpg, .gif, etc) should show up in
	 * the human readable description. Only relevent if a description was
	 * provided in the constructor or using setDescription();
	 * 
	 * @param b
	 */
	public final void setExtensionListInDescription(final boolean b) {
		useExtensionsInDescription = b;
		fullDescription = null;
	}

	/**
	 * Returns whether the extension list (.jpg, .gif, etc) should show up in
	 * the human readable description. Only relevent if a description was
	 * provided in the constructor or using setDescription();
	 * 
	 * @return true
	 */
	public final boolean isExtensionListInDescription() {
		return useExtensionsInDescription;
	}
}
