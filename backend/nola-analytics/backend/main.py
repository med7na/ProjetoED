from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from sqlalchemy import create_engine, text
from sqlalchemy.orm import sessionmaker

# ===================================
# CONFIGURAÇÕES BÁSICAS
# ===================================
DB_URL = "postgresql+psycopg2://postgres:car1100012@@localhost:5432/nola"  # 

engine = create_engine(DB_URL)
SessionLocal = sessionmaker(bind=engine, autocommit=False, autoflush=False)

app = FastAPI(title="Nola Analytics API")

# Libera o acesso do front (Lovable)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # depois podemos restringir
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# ===================================
# ROTAS DE TESTE
# ===================================

@app.get("/health")
def health():
    return {"ok": True}

@app.get("/stores")
def get_stores():
    with engine.connect() as conn:
        result = conn.execute(text("SELECT * FROM stores"))
        data = [dict(row._mapping) for row in result]
        return {"stores": data}

@app.get("/orders")
def get_orders():
    with engine.connect() as conn:
        result = conn.execute(text("SELECT * FROM orders ORDER BY created_at DESC LIMIT 10"))
        data = [dict(row._mapping) for row in result]
        return {"orders": data}
