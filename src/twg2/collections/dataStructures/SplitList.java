package twg2.collections.dataStructures;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2016-2-14
 */
public class SplitList {

	public static final <T> List<List<T>> fixedBlockSize(int blockSize, List<T> data) {
		return fixedBlockSize(blockSize, data, true);
	}


	public static final <T> List<List<T>> fixedBlockSize(int blockSize, List<T> data, boolean copySubLists) {
		List<List<T>> dst = new ArrayList<>();
		int totalSize = data.size();
		int blocks = (int)Math.ceil(totalSize / (float)blockSize);
		int lastBlockSize = (blocks * blockSize) - totalSize;

		for(int i = 0; i < blocks; i++) {
			int off = i * blockSize;
			List<T> subList = null;
			if(copySubLists) {
				subList = new ArrayList<>(blockSize);
				for(int j = 0; j < (i < blocks - 1 ? blockSize : blockSize - lastBlockSize); j++) {
					subList.add(data.get(off + j));
				}
			}
			else {
				subList = data.subList(off, off + (i < blocks - 1 ? blockSize : blockSize - lastBlockSize));
			}
			dst.add(subList);
		}
		return dst;
	}


	public static final <T> List<List<T>> fixedBlockCount(int blockCount, List<T> data) {
		return fixedBlockCount(blockCount, data, true);
	}


	public static final <T> List<List<T>> fixedBlockCount(int blockCount, List<T> data, boolean copySubLists) {
		List<List<T>> dst = new ArrayList<>(blockCount);
		int totalSize = data.size();
		int off = 0;
		int blockSize = (int)Math.round(totalSize / (float)blockCount);
		int lastRemaining = totalSize - (blockCount * blockSize);

		for(int i = 0; i < blockCount; i++) {
			int thisBlockSize = blockSize + (i == blockCount - 1 ? lastRemaining : 0);
			List<T> subList = null;
			if(copySubLists) {
				subList = new ArrayList<>(thisBlockSize);
				for(int j = 0; j < thisBlockSize; j++) {
					subList.add(data.get(off + j));
				}
			}
			else {
				subList = data.subList(off, off + thisBlockSize);
			}
			dst.add(subList);
			off += thisBlockSize;
		}
		return dst;
	}

}
