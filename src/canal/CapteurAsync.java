package canal;

import java.util.concurrent.Future;

public interface CapteurAsync {

	public Future<Integer> getValue();
}
