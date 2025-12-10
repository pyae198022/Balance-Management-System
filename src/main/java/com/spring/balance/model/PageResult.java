package com.spring.balance.model;

import java.util.ArrayList;
import java.util.List;

public record PageResult<T>(List<T> contents, long count, int size, int page) {

	public int getTotalPage() {
		Long totalPage = count / size;

		if (count % size > 0) {
			totalPage += 1;
		}

		return totalPage.intValue();

	}

	public List<Integer> getPageLinks() {
		var links = new ArrayList<Integer>();
		var totalPage = getTotalPage();

		if (totalPage > 0) {
			links.add(page);

			while (links.getFirst() > 0 && links.size() < 3) {
				links.addFirst(links.getFirst() - 1);
			}

			while (links.getLast() < totalPage - 1 && links.size() < 5) {
				links.addLast(links.getLast() + 1);
			}

			while (links.getFirst() > 0 && links.size() < 5) {
				links.addFirst(links.getFirst() - 1);
			}
		}

		return links;
	}

	public static <T> Builder<T> builder() {
		return new Builder<T>();
	}

	public static class Builder<T> {
		private List<T> contents;
		private long count;
		private int size;
		private int page;

		public Builder<T> contents(List<T> contents) {
			this.contents = contents;
			return this;
		}

		public Builder<T> count(long count) {
			this.count = count;
			return this;
		}

		public Builder<T> size(int size) {
			this.size = size;
			return this;
		}

		public Builder<T> page(int page) {
			this.page = page;
			return this;
		}

		public PageResult<T> build() {
			return new PageResult<>(contents, count, size, page);
		}
	}
}
