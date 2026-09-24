import { useState, useEffect } from 'react';
import { getAllCategories, createCategory } from '../api/categories';
import { getAllTrees, createTree, publishTree, unpublishTree } from '../api/trees';
import { getNodesByTree, createNode } from '../api/treeNodes';
import { createOption } from '../api/nodeOptions';
import { 
  Settings, FolderPlus, GitBranch, Box, Link2, 
  CheckCircle, XCircle 
} from 'lucide-react';

import { Link } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';

export default function Admin() {
  const [categories, setCategories] = useState([]);
  const [trees, setTrees] = useState([]);
  const [selectedTreeId, setSelectedTreeId] = useState('');
  const [nodes, setNodes] = useState([]);
  const [message, setMessage] = useState('');

  const [catName, setCatName] = useState('');
  const [catDesc, setCatDesc] = useState('');
  const [treeName, setTreeName] = useState('');
  const [treeCategoryId, setTreeCategoryId] = useState('');
  const [nodeType, setNodeType] = useState('QUESTION');
  const [questionText, setQuestionText] = useState('');
  const [resolutionText, setResolutionText] = useState('');
  const [parentNodeId, setParentNodeId] = useState('');
  const [optionLabel, setOptionLabel] = useState('');
  const [optionNodeId, setOptionNodeId] = useState('');
  const [nextNodeId, setNextNodeId] = useState('');

  const loadCategories = () => getAllCategories().then(res => setCategories(res.data));
  const loadTrees = () => getAllTrees().then(res => setTrees(res.data));
  const loadNodes = (treeId) => {
    if (!treeId) return setNodes([]);
    getNodesByTree(treeId).then(res => setNodes(res.data));
  };

  useEffect(() => {
    loadCategories();
    loadTrees();
  }, []);

  useEffect(() => {
    loadNodes(selectedTreeId);
  }, [selectedTreeId]);

  const showMsg = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(''), 3000);
  };

  const handleCreateCategory = async (e) => {
    e.preventDefault();
    try {
      await createCategory(catName, catDesc);
      setCatName(''); setCatDesc('');
      loadCategories();
      showMsg('Category created successfully');
    } catch {
      showMsg('Error creating category');
    }
  };

  const handleCreateTree = async (e) => {
    e.preventDefault();
    try {
      await createTree(treeCategoryId, treeName);
      setTreeName(''); setTreeCategoryId('');
      loadTrees();
      showMsg('Tree created successfully');
    } catch {
      showMsg('Error creating tree');
    }
  };

  const handlePublish = async (id, isPublished) => {
    if (isPublished) await unpublishTree(id);
    else await publishTree(id);
    loadTrees();
    showMsg(isPublished ? 'Tree unpublished' : 'Tree published');
  };

  const handleCreateNode = async (e) => {
    e.preventDefault();
    try {
      await createNode({
        treeId: Number(selectedTreeId),
        nodeType,
        questionText: nodeType === 'QUESTION' ? questionText : null,
        resolutionText: nodeType === 'LEAF' ? resolutionText : null,
        parentNodeId: parentNodeId ? Number(parentNodeId) : null
      });
      setQuestionText(''); setResolutionText(''); setParentNodeId('');
      loadNodes(selectedTreeId);
      showMsg('Node created successfully');
    } catch {
      showMsg('Error creating node');
    }
  };

  const handleCreateOption = async (e) => {
    e.preventDefault();
    try {
      await createOption(Number(optionNodeId), optionLabel, Number(nextNodeId));
      setOptionLabel(''); setOptionNodeId(''); setNextNodeId('');
      showMsg('Option created successfully');
    } catch {
      showMsg('Error creating option');
    }
  };

  return (
    <div>
      <h1 className="page-title">
        <Settings size={28} color="#0d9488" /> Admin Panel
      </h1>

      <div style={{ marginBottom: 20 }}>
  <Link to="/dashboard" style={{ 
    display: 'inline-flex', 
    alignItems: 'center', 
    gap: 6, 
    fontSize: 14, 
    fontWeight: 500,
    color: '#64748b'
  }}>
    <ArrowLeft size={16} /> Back to Dashboard
  </Link>
</div>

      <p className="page-subtitle">
        Manage categories, decision trees, nodes and options.
      </p>

      {message && (
        <div style={{
          background: '#d1fae5', color: '#065f46',
          padding: 12, borderRadius: 8, marginBottom: 20, fontWeight: 500
        }}>
          {message}
        </div>
      )}

      {/* 1. Categories */}
      <div className="card" style={{ marginBottom: 24 }}>
        <h3 style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 16 }}>
          <FolderPlus size={18} /> 1. Categories
        </h3>
        <form onSubmit={handleCreateCategory} style={{ display: 'flex', gap: 10, flexWrap: 'wrap', marginBottom: 16 }}>
          <input placeholder="Category name" value={catName} onChange={e => setCatName(e.target.value)} required style={{ flex: 1, minWidth: 160 }} />
          <input placeholder="Description (optional)" value={catDesc} onChange={e => setCatDesc(e.target.value)} style={{ flex: 1, minWidth: 160 }} />
          <button type="submit" className="btn-primary">Create</button>
        </form>
        <div style={{ display: 'flex', flexDirection: 'column', gap: 6 }}>
          {categories.map(c => (
            <div key={c.id} style={{ padding: '8px 12px', background: '#f8fafc', borderRadius: 8, fontSize: 14 }}>
              <strong>#{c.id}</strong> — {c.name} {c.description && <span style={{ color: '#64748b' }}>({c.description})</span>}
            </div>
          ))}
        </div>
      </div>

      {/* 2. Trees */}
      <div className="card" style={{ marginBottom: 24 }}>
        <h3 style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 16 }}>
          <GitBranch size={18} /> 2. Trees
        </h3>
        <form onSubmit={handleCreateTree} style={{ display: 'flex', gap: 10, flexWrap: 'wrap', marginBottom: 16 }}>
          <select value={treeCategoryId} onChange={e => setTreeCategoryId(e.target.value)} required style={{ flex: 1, minWidth: 160 }}>
            <option value="">Select Category</option>
            {categories.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
          </select>
          <input placeholder="Tree name" value={treeName} onChange={e => setTreeName(e.target.value)} required style={{ flex: 1, minWidth: 160 }} />
          <button type="submit" className="btn-primary">Create</button>
        </form>
        <div style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
          {trees.map(t => (
            <div key={t.id} style={{
              display: 'flex', justifyContent: 'space-between', alignItems: 'center',
              padding: '10px 14px', background: '#f8fafc', borderRadius: 8, flexWrap: 'wrap', gap: 8
            }}>
              <div style={{ fontSize: 14 }}>
                <strong>#{t.id}</strong> — {t.name} · {t.categoryName}
                <span className={`badge ${t.isPublished ? 'badge-green' : 'badge-yellow'}`} style={{ marginLeft: 8 }}>
                  {t.isPublished ? 'Published' : 'Draft'}
                </span>
              </div>
              <button
                onClick={() => handlePublish(t.id, t.isPublished)}
                className={t.isPublished ? 'btn-outline' : 'btn-success'}
                style={{ padding: '6px 12px', fontSize: 13 }}
              >
                {t.isPublished ? <><XCircle size={14} /> Unpublish</> : <><CheckCircle size={14} /> Publish</>}
              </button>
            </div>
          ))}
        </div>
      </div>

      {/* 3. Nodes */}
      <div className="card" style={{ marginBottom: 24 }}>
        <h3 style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 16 }}>
          <Box size={18} /> 3. Nodes
        </h3>
        <div style={{ marginBottom: 16 }}>
          <label style={{ display: 'block', fontSize: 13, fontWeight: 500, marginBottom: 6 }}>Select Tree</label>
          <select value={selectedTreeId} onChange={e => setSelectedTreeId(e.target.value)}>
            <option value="">-- Choose Tree --</option>
            {trees.map(t => <option key={t.id} value={t.id}>{t.name}</option>)}
          </select>
        </div>

        {selectedTreeId && (
          <>
            <form onSubmit={handleCreateNode} style={{ display: 'flex', flexDirection: 'column', gap: 12, marginBottom: 20 }}>
              <select value={nodeType} onChange={e => setNodeType(e.target.value)}>
                <option value="QUESTION">QUESTION</option>
                <option value="LEAF">LEAF</option>
              </select>
              {nodeType === 'QUESTION' && (
                <textarea placeholder="Question text" value={questionText} onChange={e => setQuestionText(e.target.value)} rows={2} />
              )}
              {nodeType === 'LEAF' && (
                <textarea placeholder="Resolution text" value={resolutionText} onChange={e => setResolutionText(e.target.value)} rows={3} />
              )}
              <input placeholder="Parent Node ID (empty = root)" value={parentNodeId} onChange={e => setParentNodeId(e.target.value)} />
              <button type="submit" className="btn-primary" style={{ alignSelf: 'flex-start' }}>Create Node</button>
            </form>

            <h4 style={{ fontSize: 14, color: '#64748b', marginBottom: 10 }}>Existing Nodes</h4>
            <div style={{ display: 'flex', flexDirection: 'column', gap: 6 }}>
              {nodes.map(n => (
                <div key={n.id} style={{ padding: '8px 12px', background: '#f8fafc', borderRadius: 8, fontSize: 13 }}>
                  <strong>#{n.id}</strong> · <span className={`badge ${n.nodeType === 'QUESTION' ? 'badge-blue' : 'badge-green'}`}>{n.nodeType}</span>
                  {' '}{n.nodeType === 'QUESTION' ? n.questionText : n.resolutionText?.substring(0, 60)}
                  {n.parentNodeId && <span style={{ color: '#94a3b8' }}> ← parent {n.parentNodeId}</span>}
                </div>
              ))}
            </div>
          </>
        )}
      </div>

      {/* 4. Options */}
      <div className="card">
        <h3 style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 16 }}>
          <Link2 size={18} /> 4. Node Options
        </h3>
        <form onSubmit={handleCreateOption} style={{ display: 'flex', gap: 10, flexWrap: 'wrap' }}>
          <input placeholder="From Node ID" value={optionNodeId} onChange={e => setOptionNodeId(e.target.value)} required style={{ width: 130 }} />
          <input placeholder="Option Label" value={optionLabel} onChange={e => setOptionLabel(e.target.value)} required style={{ flex: 1, minWidth: 140 }} />
          <input placeholder="Next Node ID" value={nextNodeId} onChange={e => setNextNodeId(e.target.value)} required style={{ width: 130 }} />
          <button type="submit" className="btn-primary">Create Option</button>
        </form>
        <p style={{ fontSize: 13, color: '#94a3b8', marginTop: 12 }}>
          Tip: Create a QUESTION and a LEAF first, then link them with an option.
        </p>
      </div>
    </div>
  );
}