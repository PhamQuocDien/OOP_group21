
using System;
using System.Collections.Generic;

namespace CSharp.DAO
{
    public class InMemoryDAO<T>
    {
        private readonly Dictionary<string, T> _data = new();
        private readonly Func<T, string> _idSelector;

        public InMemoryDAO(Func<T, string> idSelector)
        {
            _idSelector = idSelector
                ?? throw new ArgumentNullException(nameof(idSelector));
        }

        public List<T> GetAll()
        {
            return new List<T>(_data.Values);
        }

        public T? FindById(string id)
        {
            _data.TryGetValue(id, out T? item);
            return item;
        }

        public void Add(T item)
        {
            if (item is null)
            {
                throw new ArgumentNullException(nameof(item));
            }

            string id = _idSelector(item);

            if (string.IsNullOrWhiteSpace(id))
            {
                throw new ArgumentException("Ma dinh danh khong hop le");
            }

            if (_data.ContainsKey(id))
            {
                throw new ArgumentException(
                    "Ma dinh danh da ton tai: " + id);
            }

            _data.Add(id, item);
        }

        public bool RemoveById(string id)
        {
            if (string.IsNullOrWhiteSpace(id))
            {
                return false;
            }

            return _data.Remove(id);
        }
    }
}
