package pro.zackpollard.projectx.uploaders;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pro.zackpollard.projectx.ProjectX;
import pro.zackpollard.projectx.io.NetworkRequest;
import pro.zackpollard.projectx.utils.Regex;

import java.util.*;

@RequiredArgsConstructor
public abstract class Uploader {

	@Getter
	private final ProjectX projectX;
    @Getter
    private final String name;
    @Getter
    private final Uploader.Type type;
    @Getter
    private final List<Regex> responseFilter = new ArrayList<Regex>();
    @Getter
    private final Map<String, String> optionalHeaders = new HashMap<String, String>();
    @Getter
    private final Map<String, String> optionalParams = new HashMap<String, String>();
    @Setter
    @Getter
    private String url;
    @Setter
    @Getter
    private NetworkRequest.Method method;
    @Setter
    @Getter
    private String file;

    public void addFilter(Regex regex) {
        this.responseFilter.add(regex);
    }

    public void addOptionalHeader(String key, String value) {
        this.optionalHeaders.put(key, value);
    }

    public void addOptionalParam(String key, String value) {
        this.optionalParams.put(key, value);
    }

    public abstract UploadStatus testConnection();

    public abstract boolean isSetup();

    public abstract void runSetup();

    public enum Type {
        UNKNOWN,
        IMAGE("image", "images");

        private final static Map<String, Type> byName = new HashMap<String, Type>();

        private final Set<String> aliases = new HashSet<String>();

        private Type(String... aliases) {
            for (String alias : aliases) {
                this.aliases.add(alias.toLowerCase());
            }
        }

        static {
            for (Type type : Type.values()) {
                for (String alias : type.aliases) {
                    byName.put(alias, type);
                }
            }
        }

        public static Type getType(String name) {
            Type type = Type.byName.get(name.toLowerCase());
            return type != null ? type : Type.UNKNOWN;
        }
    }
}
