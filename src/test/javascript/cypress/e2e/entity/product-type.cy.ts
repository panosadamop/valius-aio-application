import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('ProductType e2e test', () => {
  const productTypePageUrl = '/product-type';
  const productTypePageUrlPattern = new RegExp('/product-type(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const productTypeSample = { value: 'Beauty', checkBoxValue: true, language: 'GREEK' };

  let productType;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/product-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/product-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/product-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (productType) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/product-types/${productType.id}`,
      }).then(() => {
        productType = undefined;
      });
    }
  });

  it('ProductTypes menu should load ProductTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('product-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ProductType').should('exist');
    cy.url().should('match', productTypePageUrlPattern);
  });

  describe('ProductType page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(productTypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ProductType page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/product-type/new$'));
        cy.getEntityCreateUpdateHeading('ProductType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productTypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/product-types',
          body: productTypeSample,
        }).then(({ body }) => {
          productType = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/product-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/product-types?page=0&size=20>; rel="last",<http://localhost/api/product-types?page=0&size=20>; rel="first"',
              },
              body: [productType],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(productTypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ProductType page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('productType');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productTypePageUrlPattern);
      });

      it('edit button click should load edit ProductType page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ProductType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productTypePageUrlPattern);
      });

      it('edit button click should load edit ProductType page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ProductType');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productTypePageUrlPattern);
      });

      it('last delete button click should delete instance of ProductType', () => {
        cy.intercept('GET', '/api/product-types/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('productType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productTypePageUrlPattern);

        productType = undefined;
      });
    });
  });

  describe('new ProductType page', () => {
    beforeEach(() => {
      cy.visit(`${productTypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ProductType');
    });

    it('should create an instance of ProductType', () => {
      cy.get(`[data-cy="value"]`).type('Granite Garden').should('have.value', 'Granite Garden');

      cy.get(`[data-cy="checkBoxValue"]`).should('not.be.checked');
      cy.get(`[data-cy="checkBoxValue"]`).click().should('be.checked');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        productType = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', productTypePageUrlPattern);
    });
  });
});
